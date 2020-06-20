package com.dovit.backend.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

/**
 * @author Ramón París
 * @since 12-04-20
 */
@ExtendWith(SpringExtension.class)
class EmailServiceImplTest {

  @Mock JavaMailSender javaMailSender;
  @InjectMocks EmailServiceImpl emailService;

  @Test
  void sendSimpleMessage_OK() {
    doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
    //    emailService.sendSimpleMessage("rparis@retailsbs.com", "subject", "text");
  }

  @Test
  void sendSimpleMessage_EXCEPTION() {
    doThrow(MailSendException.class).when(javaMailSender).send(any(SimpleMailMessage.class));
    //    emailService.sendSimpleMessage("rparis@retailsbs.com", "subject", "text");
  }
}
