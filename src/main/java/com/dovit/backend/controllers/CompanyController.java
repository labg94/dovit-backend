package com.dovit.backend.controllers;

import com.dovit.backend.annotations.IsAuthenticated;
import com.dovit.backend.annotations.IsMainAdmin;
import com.dovit.backend.domain.Company;
import com.dovit.backend.payloads.requests.CompanyRequest;
import com.dovit.backend.payloads.responses.ApiResponse;
import com.dovit.backend.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * @author Ramón París
 * @since 03-10-2019
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@IsMainAdmin
@RequiredArgsConstructor
public class CompanyController {

  private final CompanyService companyService;

  @IsAuthenticated
  @GetMapping("/companies")
  public ResponseEntity<?> findAll() {
    return ResponseEntity.ok().body(companyService.findAll());
  }

  @IsAuthenticated
  @GetMapping("/company/{companyId}")
  public ResponseEntity<?> findById(@PathVariable Long companyId) {
    return ResponseEntity.ok(companyService.findCompanyResponseById(companyId));
  }

  @PostMapping("/company")
  public ResponseEntity<?> createCompany(@Valid @RequestBody CompanyRequest companyRequest) {
    Company response = companyService.createCompany(companyRequest);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(
            ApiResponse.builder()
                .id(response.getId())
                .success(true)
                .message("Company created successfully")
                .build());
  }

  @PutMapping("/company")
  public ResponseEntity<?> updateCompany(@Valid @RequestBody CompanyRequest companyRequest) {
    Company response = companyService.updateCompany(companyRequest);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(
            ApiResponse.builder()
                .id(response.getId())
                .success(true)
                .message("Company updated successfully")
                .build());
  }
}
