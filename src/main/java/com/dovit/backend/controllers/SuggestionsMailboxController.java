package com.dovit.backend.controllers;

import com.dovit.backend.domain.SuggestionMailbox;
import com.dovit.backend.payloads.requests.SuggestionRequest;
import com.dovit.backend.payloads.responses.ApiResponse;
import com.dovit.backend.services.SuggestionMailboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * @author Ramón París
 * @since 24-05-20
 */
@RestController
@RequestMapping("/api")
@Secured({"ROLE_ADMIN"})
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SuggestionsMailboxController {

  private final SuggestionMailboxService suggestionMailboxService;

  @GetMapping("/suggestions")
  public ResponseEntity<?> findAll() {
    return ResponseEntity.ok(suggestionMailboxService.findAll());
  }

  @GetMapping("/suggestion/{id}")
  public ResponseEntity<?> findById(@PathVariable Long id) {
    return ResponseEntity.ok(suggestionMailboxService.findById(id));
  }

  @PostMapping("/suggestion")
  @Secured({"ROLE_ADMIN", "ROLE_CLIENT"})
  public ResponseEntity<?> makeSuggestion(@RequestBody @Valid SuggestionRequest request) {
    SuggestionMailbox response = suggestionMailboxService.makeSuggestion(request);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(
            ApiResponse.builder()
                .success(true)
                .message("Suggestion created successfully")
                .id(response.getId())
                .build());
  }

  @PatchMapping("/suggestion/{id}/viewed")
  public ResponseEntity<?> toggleViewed(@PathVariable Long id) {
    suggestionMailboxService.toggleViewed(id);
    return ResponseEntity.ok((new ApiResponse(true, "Suggestion toggled successfully")));
  }
}
