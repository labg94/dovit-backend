package com.dovit.backend.controller;

import com.dovit.backend.domain.Resource;
import com.dovit.backend.service.ResourceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ResourceController {

    private final ResourceService projectService;

    @GetMapping
    public ResponseEntity<Resource> getId(@RequestParam Long id) {
        return ResponseEntity.ok(projectService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Resource>> getAll() {
        return ResponseEntity.ok(projectService.findAll());
    }

    @PutMapping
    public ResponseEntity<Resource> update(@RequestBody Resource resource) {
        return ResponseEntity.ok(projectService.updateResource(resource));
    }
}
