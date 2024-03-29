package com.dovit.backend.controllers;

import com.dovit.backend.annotations.IsAnyAdmin;
import com.dovit.backend.annotations.IsAuthenticated;
import com.dovit.backend.domain.CompanyLicense;
import com.dovit.backend.payloads.requests.CompanyLicenseRequest;
import com.dovit.backend.payloads.responses.ApiResponse;
import com.dovit.backend.services.CompanyLicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * Controller to show the COMPANY LICENSES!
 *
 * @author Ramón París
 * @since 14-10-2019
 */
@RestController
@RequestMapping("/api")
@IsAnyAdmin
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CompanyLicenseController {

  private final CompanyLicenseService companyLicenseService;

  /**
   * Shows all the licenses of a company of a certain tool
   *
   * @return Entity with list of CompanyLicensesResponse
   */
  @IsAuthenticated
  @GetMapping("/company/{companyId}/tool/{toolId}")
  public ResponseEntity<?> findLicensesByToolId(
      @PathVariable Long companyId, @PathVariable Long toolId) {
    return ResponseEntity.ok(companyLicenseService.findAllByCompanyIdAndToolId(companyId, toolId));
  }

  @IsAuthenticated
  @GetMapping("/company/{companyId}/licenses")
  public ResponseEntity<?> findAllLicenses(@PathVariable Long companyId) {
    return ResponseEntity.ok(companyLicenseService.findAllByCompanyId(companyId));
  }

  @PostMapping("/company/license")
  public ResponseEntity<?> createCompanyLicense(@RequestBody @Valid CompanyLicenseRequest request) {
    CompanyLicense response = companyLicenseService.createCompanyLicense(request);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(new ApiResponse(true, "Company license created successfully"));
  }

  @PutMapping("/company/license")
  public ResponseEntity<?> updateCompanyLicense(@RequestBody @Valid CompanyLicenseRequest request) {
    CompanyLicense response = companyLicenseService.updateCompanyLicense(request);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(new ApiResponse(true, "Company license updated successfully"));
  }

  @DeleteMapping("/company/license/{companyLicenseId}")
  public ResponseEntity<?> deleteCompanyLicense(@PathVariable Long companyLicenseId) {
    Boolean resp = companyLicenseService.deleteCompanyLicense(companyLicenseId);
    return ResponseEntity.ok(new ApiResponse(resp, "License deleted"));
  }
}
