package com.dovit.backend.services;

import com.dovit.backend.model.SuggestionEmailDTO;

/**
 * @author Ramón París
 * @since 07-12-2019
 */
public interface EmailService {

  void sendRegistration(String to, String registrationLink);

  void sendSuggestion(String to, SuggestionEmailDTO emailDTO);
}
