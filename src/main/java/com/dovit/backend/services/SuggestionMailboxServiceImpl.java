package com.dovit.backend.services;

import com.dovit.backend.domain.SuggestionMailbox;
import com.dovit.backend.domain.User;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.SuggestionEmailDTO;
import com.dovit.backend.payloads.requests.SuggestionRequest;
import com.dovit.backend.payloads.responses.SuggestionResponse;
import com.dovit.backend.repositories.SuggestionMailboxRepository;
import com.dovit.backend.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 24-05-20
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SuggestionMailboxServiceImpl implements SuggestionMailboxService {

  private final SuggestionMailboxRepository suggestionMailboxRepository;
  private final EmailService emailService;
  private final ModelMapper modelMapper;

  @Value("${dovit.manager.emails}")
  private String[] MANAGER_EMAILS;

  @Override
  public List<SuggestionResponse> findAll() {
    List<SuggestionMailbox> suggestionMailboxes = suggestionMailboxRepository.findAll();
    return suggestionMailboxes.stream()
        .map(suggestionMailbox -> modelMapper.map(suggestionMailbox, SuggestionResponse.class))
        .collect(Collectors.toList());
  }

  @Override
  public SuggestionResponse findById(Long suggestionId) {
    SuggestionMailbox suggestionMailbox = findDomainById(suggestionId);
    return modelMapper.map(suggestionMailbox, SuggestionResponse.class);
  }

  private SuggestionMailbox findDomainById(Long suggestionId) {
    return suggestionMailboxRepository
        .findById(suggestionId)
        .orElseThrow(() -> new ResourceNotFoundException("Suggestion", "id", suggestionId));
  }

  @Override
  @Transactional
  public SuggestionMailbox makeSuggestion(SuggestionRequest request) {
    UserPrincipal userLogged =
        (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    final SuggestionMailbox suggestionMailbox = modelMapper.map(request, SuggestionMailbox.class);
    if (!userLogged.getId().equals(0L)) {
      suggestionMailbox.setSuggestedBy(User.builder().id(userLogged.getId()).build());
    }
    SuggestionMailbox persistedSuggestion = suggestionMailboxRepository.save(suggestionMailbox);

    final SuggestionEmailDTO suggestionEmailDTO =
        generateEmailBody(persistedSuggestion, userLogged);
    Arrays.asList(MANAGER_EMAILS)
        .forEach(email -> emailService.sendSuggestion(email, suggestionEmailDTO));
    log.info("Message sent to {}", Arrays.toString(MANAGER_EMAILS));
    return persistedSuggestion;
  }

  @Override
  public void toggleViewed(Long suggestionId) {
    SuggestionMailbox suggestionMailbox = this.findDomainById(suggestionId);
    suggestionMailbox.setViewed(!suggestionMailbox.isViewed());
    suggestionMailboxRepository.save(suggestionMailbox);
  }

  private SuggestionEmailDTO generateEmailBody(
      SuggestionMailbox persistedSuggestion, UserPrincipal userPrincipal) {

    return SuggestionEmailDTO.builder()
        .issuerName(userPrincipal.getName() + " " + userPrincipal.getLastName())
        .companyName(userPrincipal.getCompanyName())
        .toolName(persistedSuggestion.getTool())
        .category(
            persistedSuggestion.getCategory() != null
                ? persistedSuggestion.getCategory().getDescription()
                : "Other")
        .subCategory(
            persistedSuggestion.getSubcategory() != null
                ? persistedSuggestion.getSubcategory().getDescription()
                : "Other")
        .message(persistedSuggestion.getMessage())
        .issuerEmail(userPrincipal.getEmail())
        .build();
  }
}
