package com.dovit.backend.controller;

import com.dovit.backend.domain.Project;
import com.dovit.backend.domain.Tool;
import com.dovit.backend.service.ToolService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ToolController {

    private final ToolService projectService;

    @GetMapping
    public ResponseEntity<Tool> getId(@RequestParam Long id) {
        return ResponseEntity.ok(projectService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Tool>> getAll() {
        return ResponseEntity.ok(projectService.findAll());
    }

    @PutMapping
    public ResponseEntity<Tool> update(@RequestBody Tool tool) {
        return ResponseEntity.ok(projectService.updateTool(tool));
    }

    @PostMapping
    public ResponseEntity<List<Tool>> generatePipeline(@RequestBody Project project) {
        return ResponseEntity.ok(projectService.generatePipeline(project));
    }
}
