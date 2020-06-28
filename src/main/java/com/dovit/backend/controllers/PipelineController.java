package com.dovit.backend.controllers;

import com.dovit.backend.annotations.IsAnyAdmin;
import com.dovit.backend.annotations.IsAuthenticated;
import com.dovit.backend.payloads.requests.PipelineRecommendationRequest;
import com.dovit.backend.services.PipelineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ramón París
 * @since 31-05-20
 */
@RequestMapping("/api")
@IsAnyAdmin
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
public class PipelineController {

  private final PipelineService pipelineService;

  @PostMapping("/pipeline/recommendation")
  public ResponseEntity<?> findPipelineRecommendation(
      @RequestBody PipelineRecommendationRequest request) {
    return ResponseEntity.ok(pipelineService.generatePipelineRecommendation(request));
  }

  @IsAuthenticated
  @GetMapping("/project/{projectId}/pipelines")
  public ResponseEntity<?> findAllByProjectId(@PathVariable Long projectId) {
    //    return ResponseEntity.ok(pipelineService.generatePipelineRecommendation(request));
    return null;
  }
}
