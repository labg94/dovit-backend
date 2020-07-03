package com.dovit.backend.services;

import com.dovit.backend.model.SuggestionEmailDTO;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ramón París
 * @since 07-12-2019
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

  @Value("${spring.sendgrid.api-key}")
  private String SENDGRID_API_KEY;

  private final FreeMarkerConfigurer freeMarkerConfigurer;

  private void sendMail(String to, String subject, String text) {
    new Thread(
            () -> {
              Mail mail =
                  new Mail(
                      new Email("no-reply@dovit.cl"), // from
                      subject, // subject
                      new Email(to), // to
                      new Content("text/html", text)); // content

              try {
                final Request request = new Request();
                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());

                final SendGrid sendGrid = new SendGrid(SENDGRID_API_KEY);
                final Response response = sendGrid.api(request);
                log.info("Email to {} responded with HttpStatus {}", to, response.getStatusCode());

              } catch (IOException ex) {
                log.error("Error sending email '{}' to {}", subject, to);
              }
            })
        .start();
  }

  @Override
  public void sendSuggestion(String to, SuggestionEmailDTO emailDTO) {
    final Map<String, Object> variables =
        new HashMap<>() {
          {
            put("suggestion", emailDTO);
          }
        };

    try {
      Template freemarkerTemplate =
          freeMarkerConfigurer.createConfiguration().getTemplate("suggestion.ftl");
      String htmlBody =
          FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, variables);
      this.sendMail(to, "DOV IT - Tool suggestion", htmlBody);
    } catch (IOException | TemplateException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void sendRegistration(String to, String registrationLink) {
    final Map<String, Object> variables =
        new HashMap<>() {
          {
            put("registrationLink", registrationLink);
          }
        };

    try {
      Template freemarkerTemplate =
          freeMarkerConfigurer.createConfiguration().getTemplate("registration.ftl");
      String htmlBody =
          FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, variables);
      this.sendMail(to, "DOV IT - Registration", htmlBody);
    } catch (IOException | TemplateException e) {
      e.printStackTrace();
    }
  }
}
