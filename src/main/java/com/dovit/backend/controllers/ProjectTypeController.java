package com.dovit.backend.controllers;

import com.dovit.backend.domain.ProjectType;
import com.dovit.backend.payloads.requests.ProjectTypeRequest;
import com.dovit.backend.payloads.responses.ApiResponse;
import com.dovit.backend.services.ProjectTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * @author Ramón París
 * @since 31-05-20
 */
@RequestMapping("/api")
@Secured({"ROLE_ADMIN", "ROLE_CLIENT"})
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
public class ProjectTypeController {

  private final ProjectTypeService projectTypeService;

  @GetMapping("/project/types")
  public ResponseEntity<?> findAllProjectTypes() {
    return ResponseEntity.ok(projectTypeService.findAll());
  }

  @PostMapping("/project/type")
  public ResponseEntity<?> save(@RequestBody @Valid ProjectTypeRequest request) {
    ProjectType response = projectTypeService.save(request);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(
            ApiResponse.builder()
                .id(response.getId())
                .message("Project type created successfully")
                .success(true)
                .build());
  }

  @PutMapping("/project/type")
  public ResponseEntity<?> update(@RequestBody @Valid ProjectTypeRequest request) {
    ProjectType response = projectTypeService.update(request);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(
            ApiResponse.builder()
                .id(response.getId())
                .message("Project type updated successfully")
                .success(true)
                .build());
  }
}
