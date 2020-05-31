package com.dovit.backend.controllers;

import com.dovit.backend.services.LicensePayCycleService;
import com.dovit.backend.services.LicenseTypeService;
import com.dovit.backend.services.ProjectTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class MasterTablesController {

  private final LicensePayCycleService licensePayCycleService;
  private final LicenseTypeService licenseTypeService;
  private final ProjectTypeService projectTypeService;

  @GetMapping("/license/payCycles")
  public ResponseEntity<?> findAllPayCycles() {
    return ResponseEntity.ok(licensePayCycleService.findAll());
  }

  @GetMapping("/license/types")
  public ResponseEntity<?> findAllTypes() {
    return ResponseEntity.ok(licenseTypeService.findAll());
  }

  @GetMapping("/project/types")
  public ResponseEntity<?> findAllProjectTypes() {
    return ResponseEntity.ok(projectTypeService.findAll());
  }
}
