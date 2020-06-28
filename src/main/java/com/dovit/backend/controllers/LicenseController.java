package com.dovit.backend.controllers;

import com.dovit.backend.annotations.IsAuthenticated;
import com.dovit.backend.annotations.IsMainAdmin;
import com.dovit.backend.domain.License;
import com.dovit.backend.payloads.requests.LicenseRequest;
import com.dovit.backend.payloads.responses.ApiResponse;
import com.dovit.backend.services.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * Controller to show the general licenses of the tools. NOT ASSOCIATED WITH A COMPANY.
 *
 * @author Ramón París
 * @since 04-10-2019
 */
@RestController
@RequestMapping("/api")
@IsMainAdmin
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class LicenseController {

  private final LicenseService licenseService;

  @IsAuthenticated
  @GetMapping("/tool/{toolId}/licenses")
  public ResponseEntity<?> findAllLicensesOfTool(@PathVariable Long toolId) {
    return ResponseEntity.ok(licenseService.findAllByToolId(toolId));
  }

  @IsAuthenticated
  @GetMapping("/tool/{toolId}/licenses/active")
  public ResponseEntity<?> findAllActiveLicensesOfTool(@PathVariable Long toolId) {
    return ResponseEntity.ok(licenseService.findAllActivesByToolId(toolId));
  }

  @PostMapping("/license")
  public ResponseEntity<?> save(@RequestBody @Valid LicenseRequest licenseRequest) {
    License response = licenseService.save(licenseRequest);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(new ApiResponse(true, "License created successfully"));
  }

  @PutMapping("/license")
  public ResponseEntity<?> update(@RequestBody @Valid LicenseRequest licenseRequest) {
    License response = licenseService.update(licenseRequest);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(new ApiResponse(true, "License updated successfully"));
  }

  @PatchMapping("/license/{id}/active")
  public ResponseEntity<?> toggleActive(@PathVariable Long id) {
    licenseService.toggleActive(id);
    return ResponseEntity.ok(new ApiResponse(true, "License toggled"));
  }
}
