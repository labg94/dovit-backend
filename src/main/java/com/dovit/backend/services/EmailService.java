package com.dovit.backend.services;

/**
 * @author Ramón París
 * @since 07-12-2019
 */
public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);
}
