package com.dovit.backend.controllers;

import com.dovit.backend.services.ToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to show the TOOLS in different ways
 *
 * @author Ramón París
 * @since 14-10-2019
 */
@RestController
@RequestMapping("/api")
@Secured({"ROLE_ADMIN", "ROLE_CLIENT"})
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ToolController {

  private final ToolService toolService;

  @GetMapping("/tools")
  public ResponseEntity<?> findAll() {
    return ResponseEntity.ok(toolService.findAllTools());
  }

  @GetMapping("/tools/categories")
  public ResponseEntity<?> findAllToolsGroupedBy() {
    return ResponseEntity.ok(toolService.findAllToolsGroupedByCats());
  }

  @GetMapping("/company/{companyId}/tools")
  public ResponseEntity<?> findAllToolsByCompany(@PathVariable Long companyId) {
    return ResponseEntity.ok(toolService.findAllToolsOfCompany(companyId));
  }

  @GetMapping("/tool/{toolId}")
  public ResponseEntity<?> findById(@PathVariable Long toolId) {
    return ResponseEntity.ok(toolService.findById(toolId));
  }
}
