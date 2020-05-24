package com.dovit.backend.services;

import com.dovit.backend.domain.SuggestionMailbox;
import com.dovit.backend.model.requests.SuggestionRequest;
import com.dovit.backend.model.responses.SuggestionResponse;

import java.util.List;

/**
 * @author Ramón París
 * @since 24-05-20
 */
public interface SuggestionMailboxService {

  List<SuggestionResponse> findAll();

  SuggestionResponse findById(Long suggestionId);

  SuggestionMailbox makeSuggestion(SuggestionRequest request);

  void toggleViewed(Long suggestionId);
}
