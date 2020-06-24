package com.dovit.backend.controllers;

import com.dovit.backend.domain.Holding;
import com.dovit.backend.payloads.requests.HoldingRequest;
import com.dovit.backend.payloads.responses.ApiResponse;
import com.dovit.backend.services.HoldingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ramón París
 * @since 23-06-20
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Secured("ROLE_ADMIN")
public class HoldingController {

  private final HoldingService holdingService;

  @GetMapping("/holdings")
  @Secured({"ROLE_ADMIN", "ROLE_CLIENT"})
  public ResponseEntity<?> findAllHoldings() {
    return ResponseEntity.ok(holdingService.findAll());
  }

  @GetMapping("/holding/{holdingId}")
  @Secured({"ROLE_ADMIN", "ROLE_CLIENT"})
  public ResponseEntity<?> findById(@PathVariable Long holdingId) {
    return ResponseEntity.ok(holdingService.findResponseById(holdingId));
  }

  @PostMapping("/holding")
  public ResponseEntity<?> save(@RequestBody HoldingRequest holdingRequest) {
    Holding holding = holdingService.save(holdingRequest);

    return ResponseEntity.ok(
        ApiResponse.builder()
            .id(holding.getId())
            .success(true)
            .message("Created successfully")
            .build());
  }

  @PutMapping("/holding")
  public ResponseEntity<?> update(@RequestBody HoldingRequest holdingRequest) {
    Holding holding = holdingService.update(holdingRequest);

    return ResponseEntity.ok(
        ApiResponse.builder()
            .id(holding.getId())
            .success(true)
            .message("Updated successfully")
            .build());
  }
}
