package com.dovit.backend.controllers;

import com.dovit.backend.domain.Project;
import com.dovit.backend.model.requests.ProjectRequest;
import com.dovit.backend.model.responses.ApiResponse;
import com.dovit.backend.model.responses.ProjectResponse;
import com.dovit.backend.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
@Secured({"ROLE_ADMIN","ROLE_CLIENT"})
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/company/{companyId}/projects")
    public List<ProjectResponse> findAllByCompanyId(@PathVariable Long companyId){
        return projectService.findAllByCompanyId(companyId);
    }

    @GetMapping("/project/{projectId}")
    public ProjectResponse findByProjectId(@PathVariable Long projectId){
        return projectService.findByProjectId(projectId);
    }

//    @GetMapping("/company/{companyId}/project/availableMembers")
//    public List<>

    @PostMapping("/project")
    public ResponseEntity<?> createProject(@RequestBody ProjectRequest request){
        Project response = projectService.saveProject(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Project created successfully"));

    }

    @PutMapping("/project")
    public ResponseEntity<?> updateProject(@RequestBody ProjectRequest request){
        Project response = projectService.updateProject(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Project updated successfully"));

    }

}
