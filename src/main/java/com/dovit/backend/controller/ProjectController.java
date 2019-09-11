package com.dovit.backend.controller;

import com.dovit.backend.domain.Project;
import com.dovit.backend.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<Project> getId(@RequestParam Long id) {
        return ResponseEntity.ok(projectService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAll() {
        return ResponseEntity.ok(projectService.findAll());
    }

    @PutMapping
    public ResponseEntity<Project> update(@RequestBody Project project) {
        return ResponseEntity.ok(projectService.updateProject(project));
    }
}
