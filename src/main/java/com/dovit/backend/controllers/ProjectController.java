package com.dovit.backend.controllers;

import com.dovit.backend.annotations.IsAnyAdmin;
import com.dovit.backend.annotations.IsAuthenticated;
import com.dovit.backend.domain.Project;
import com.dovit.backend.payloads.requests.PipelineToolRequest;
import com.dovit.backend.payloads.requests.ProjectMemberRequest;
import com.dovit.backend.payloads.requests.ProjectRequest;
import com.dovit.backend.payloads.requests.ProjectResumeRequest;
import com.dovit.backend.payloads.responses.ApiResponse;
import com.dovit.backend.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * @author Ramón París
 * @since 09-12-2019
 */
@RequestMapping("/api")
@IsAnyAdmin
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
public class ProjectController {

  private final ProjectService projectService;

  @IsAuthenticated
  @GetMapping("/company/{companyId}/projects")
  public ResponseEntity<?> findAllByCompanyId(@PathVariable Long companyId) {
    return ResponseEntity.ok(projectService.findAllByCompanyId(companyId));
  }

  @IsAuthenticated
  @GetMapping("/project/{projectId}")
  public ResponseEntity<?> findByProjectId(@PathVariable Long projectId) {
    return ResponseEntity.ok(projectService.findByProjectId(projectId));
  }

  @IsAuthenticated
  @GetMapping("/company/{companyId}/project/availableMembers")
  public ResponseEntity<?> getMembersRecommendation(@PathVariable Long companyId) {
    return ResponseEntity.ok(projectService.findMemberRecommendation(companyId));
  }

  @PostMapping("/project")
  public ResponseEntity<?> createProject(@RequestBody @Valid ProjectRequest request) {
    Project response = projectService.saveProject(request);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(
            ApiResponse.builder()
                .success(true)
                .message("Project created successfully")
                .id(response.getId())
                .build());
  }

  @PatchMapping("/project/resume")
  public ResponseEntity<?> updateProjectResume(@RequestBody @Valid ProjectResumeRequest request) {
    Project response = projectService.updateProjectResume(request);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(new ApiResponse(true, "Project updated successfully"));
  }

  @PatchMapping("/project/{projectId}/projectType")
  public ResponseEntity<?> updateProjectTypes(
      @RequestBody List<Long> projectTypeIds, @PathVariable Long projectId) {
    Project response = projectService.updateProjectTypes(projectTypeIds, projectId);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(new ApiResponse(true, "Project updated successfully"));
  }

  @PatchMapping("/project/{projectId}/members")
  public ResponseEntity<?> updateProjectMembers(
      @RequestBody List<ProjectMemberRequest> members, @PathVariable Long projectId) {
    Project response = projectService.updateProjectMembers(members, projectId);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(new ApiResponse(true, "Project updated successfully"));
  }

  @PatchMapping("/project/{projectId}/pipeline")
  public ResponseEntity<?> updateProjectPipeline(
      @RequestBody List<PipelineToolRequest> pipelines, @PathVariable Long projectId) {
    Project response = projectService.updateProjectPipeline(pipelines, projectId);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(new ApiResponse(true, "Project updated successfully"));
  }
}
