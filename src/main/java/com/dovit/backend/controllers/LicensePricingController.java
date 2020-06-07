package com.dovit.backend.controllers;

import com.dovit.backend.domain.LicensePricing;
import com.dovit.backend.payloads.requests.LicensePricingRequest;
import com.dovit.backend.payloads.responses.ApiResponse;
import com.dovit.backend.services.LicensePricingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * @author Ramón París
 * @since 23-05-20
 */
@RestController
@RequestMapping("/api")
@Secured({"ROLE_ADMIN", "ROLE_CLIENT"})
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class LicensePricingController {

  private final LicensePricingService licensePricingService;

  @GetMapping("/licensePricing/{id}")
  ResponseEntity<?> findById(@PathVariable Long id) {
    return ResponseEntity.ok(licensePricingService.findResponseById(id));
  }

  @DeleteMapping("/licensePricing/{id}")
  ResponseEntity<?> delete(@PathVariable Long id) {
    licensePricingService.delete(id);
    log.info("License pricing with id {} deleted", id);
    return ResponseEntity.ok(new ApiResponse(true, "Deleted successfully"));
  }

  @PostMapping("/license/{licenseId}/pricing")
  ResponseEntity<?> save(
      @PathVariable Long licenseId, @RequestBody @Valid LicensePricingRequest request) {
    LicensePricing response = licensePricingService.save(request, licenseId);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(new ApiResponse(true, "License pricing created successfully"));
  }

  @PutMapping("/license/{licenseId}/pricing")
  ResponseEntity<?> update(
      @PathVariable Long licenseId, @RequestBody @Valid LicensePricingRequest request) {
    LicensePricing response = licensePricingService.update(request, licenseId);
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(new ApiResponse(true, "License pricing updated successfully"));
  }
}
