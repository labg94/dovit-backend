package com.dovit.backend.controllers;

import com.dovit.backend.annotations.IsAuthenticated;
import com.dovit.backend.annotations.IsMainAdmin;
import com.dovit.backend.domain.Tool;
import com.dovit.backend.payloads.requests.ToolRequest;
import com.dovit.backend.payloads.responses.ApiResponse;
import com.dovit.backend.services.ToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * Controller to show the TOOLS in different ways
 *
 * @author Ramón París
 * @since 14-10-2019
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@IsMainAdmin
public class ToolController {

  private final ToolService toolService;

  @GetMapping("/tools")
  @IsAuthenticated
  public ResponseEntity<?> findAll() {
    return ResponseEntity.ok(toolService.findAllTools());
  }

  @GetMapping("/tools/active")
  @IsAuthenticated
  public ResponseEntity<?> findAllActives() {
    return ResponseEntity.ok(toolService.findAllActiveTools());
  }

  @GetMapping("/subcategory/{subcategoryId}/tools/active")
  @IsAuthenticated
  public ResponseEntity<?> findAllActivesBySubcategoryId(@PathVariable Long subcategoryId) {
    return ResponseEntity.ok(toolService.findAllActiveBySubcategory(subcategoryId));
  }

  @GetMapping("/subcategory/{subcategoryId}/tools")
  @IsAuthenticated
  public ResponseEntity<?> findAllBySubcategoryId(@PathVariable Long subcategoryId) {
    return ResponseEntity.ok(toolService.findAllBySubcategory(subcategoryId));
  }

  @GetMapping("/company/{companyId}/tools")
  @IsAuthenticated
  public ResponseEntity<?> findAllToolsByCompany(@PathVariable Long companyId) {
    return ResponseEntity.ok(toolService.findAllToolsOfCompany(companyId));
  }

  @GetMapping("/tool/{toolId}")
  @IsAuthenticated
  public ResponseEntity<?> findById(@PathVariable Long toolId) {
    return ResponseEntity.ok(toolService.findById(toolId));
  }

  @PostMapping("/tools/projectTypes")
  @IsAuthenticated
  public ResponseEntity<?> findAllByProjectTypes(@RequestBody List<Long> projectTypes) {
    return ResponseEntity.ok(toolService.findAllByProjectTypes(projectTypes));
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
