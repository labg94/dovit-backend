package com.dovit.backend.controllers;

import com.dovit.backend.annotations.IsAuthenticated;
import com.dovit.backend.annotations.IsMainAdmin;
import com.dovit.backend.payloads.requests.ToolProjectTypeRequest;
import com.dovit.backend.payloads.responses.ApiResponse;
import com.dovit.backend.services.ToolProjectTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ramón París
 * @since 08-06-20
 */
@RequestMapping("/api")
@IsMainAdmin
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
public class ToolProjectTypeController {

  private final ToolProjectTypeService toolProjectTypeService;

  @GetMapping("/tool/{toolId}/projectTypes")
  @IsAuthenticated
  public ResponseEntity<?> findAllByToolId(@PathVariable Long toolId) {
    return ResponseEntity.ok(toolProjectTypeService.findAllByToolId(toolId));
  }

  @PostMapping("/tool/projectType")
  public ResponseEntity<?> save(@RequestBody ToolProjectTypeRequest request) {
    toolProjectTypeService.save(request);
    return ResponseEntity.ok(
        ApiResponse.builder().message("Created successfully").success(true).build());
  }

  @DeleteMapping("/tool/{toolId}/projectType/{projectTypeId}")
  public ResponseEntity<?> delete(@PathVariable Long toolId, @PathVariable Long projectTypeId) {
    toolProjectTypeService.delete(toolId, projectTypeId);
    return ResponseEntity.ok(
        ApiResponse.builder().message("Deleted successfully").success(true).build());
  }
}
