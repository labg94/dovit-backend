package com.dovit.backend.controllers;

import com.dovit.backend.domain.Tool;
import com.dovit.backend.payloads.requests.ToolRequest;
import com.dovit.backend.payloads.responses.ApiResponse;
import com.dovit.backend.services.ToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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

  @GetMapping("/tools/active")
  public ResponseEntity<?> findAllActives() {
    return ResponseEntity.ok(toolService.findAllActiveTools());
  }

  @GetMapping("/subcategory/{subcategoryId}/tools/active")
  public ResponseEntity<?> findAllActivesBySubcategoryId(@PathVariable Long subcategoryId) {
    return ResponseEntity.ok(toolService.findAllActiveBySubcategory(subcategoryId));
  }

  @GetMapping("/subcategory/{subcategoryId}/tools")
  public ResponseEntity<?> findAllBySubcategoryId(@PathVariable Long subcategoryId) {
    return ResponseEntity.ok(toolService.findAllBySubcategory(subcategoryId));
  }

  @GetMapping("/company/{companyId}/tools")
  public ResponseEntity<?> findAllToolsByCompany(@PathVariable Long companyId) {
    return ResponseEntity.ok(toolService.findAllToolsOfCompany(companyId));
  }

  @GetMapping("/tool/{toolId}")
  public ResponseEntity<?> findById(@PathVariable Long toolId) {
    return ResponseEntity.ok(toolService.findById(toolId));
  }

  @PostMapping("/tool")
  public ResponseEntity<?> save(@RequestBody @Valid ToolRequest request) {
    Tool response = toolService.save(request);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(
            ApiResponse.builder()
                .success(true)
                .message("Tool created successfully")
                .id(response.getId())
                .build());
  }

  @PutMapping("/tool")
  public ResponseEntity<?> update(@RequestBody @Valid ToolRequest request) {
    Tool response = toolService.update(request);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(
            ApiResponse.builder()
                .success(true)
                .message("Tool updated successfully")
                .id(response.getId())
                .build());
  }

  @PatchMapping("/tool/{id}/active")
  public ResponseEntity<?> toggleActive(@PathVariable Long id) {
    toolService.toggleActive(id);
    return ResponseEntity.ok(new ApiResponse(true, "Tool toggled"));
  }
}
