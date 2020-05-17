package com.dovit.backend.controllers;

import com.dovit.backend.domain.Company;
import com.dovit.backend.model.requests.CompanyRequest;
import com.dovit.backend.model.responses.ApiResponse;
import com.dovit.backend.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
@Secured("ROLE_ADMIN")
@RequiredArgsConstructor
public class CompanyController {

  private final CompanyService companyService;

  @GetMapping("/companies")
  public ResponseEntity<?> findAll() {
    return ResponseEntity.ok().body(companyService.findAll());
  }

  @Secured({"ROLE_CLIENT", "ROLE_ADMIN"})
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
        .body(new ApiResponse(true, "Company created successfully"));
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
        .body(new ApiResponse(true, "Company updated successfully"));
  }
}
