package com.dovit.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author Ramón París
 * @since 07-12-2019
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender mailSender;

  public void sendSimpleMessage(String to, String subject, String text) {
    new Thread(
            () -> {
              SimpleMailMessage message = new SimpleMailMessage();
              message.setFrom("ramon.paris@inacapmail.cl");
              message.setTo(to);
              message.setSubject(subject);
              message.setText(text);

              try {
                mailSender.send(message);
              } catch (MailException e) {
                log.error("Email did not send to {} with subject {}", to, subject, e);
              }
            })
        .start();
  }
}
