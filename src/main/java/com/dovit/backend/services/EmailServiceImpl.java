package com.dovit.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * @author Ramón París
 * @since 07-12-2019
 */
@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    public JavaMailSender mailSender;

    public void sendSimpleMessage(String to, String subject, String text){
        new Thread(()->{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("ramon.paris@inacapmail.cl");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            try {
                mailSender.send(message);
            } catch (MailException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
