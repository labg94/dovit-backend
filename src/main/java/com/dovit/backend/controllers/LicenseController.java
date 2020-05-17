package com.dovit.backend.controllers;

import com.dovit.backend.services.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to show the general licenses of the tools. NOT ASSOCIATED WITH A COMPANY.
 *
 * @author Ramón París
 * @since 04-10-2019
 */
@RestController
@RequestMapping("/api")
@Secured({"ROLE_ADMIN", "ROLE_CLIENT"})
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class LicenseController {

  private final LicenseService licenseService;

  @GetMapping("/tool/{toolId}/licenses")
  public ResponseEntity<?> findAllLicensesOfTool(@PathVariable Long toolId) {
    return ResponseEntity.ok(licenseService.findAllByToolId(toolId));
  }
}
