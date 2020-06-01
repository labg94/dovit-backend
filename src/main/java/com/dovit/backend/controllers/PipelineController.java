package com.dovit.backend.controllers;

import com.dovit.backend.model.requests.PipelineRecommendationRequest;
import com.dovit.backend.services.PipelineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ramón París
 * @since 31-05-20
 */
@RequestMapping("/api")
@Secured({"ROLE_ADMIN"})
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
public class PipelineController {

  private final PipelineService pipelineService;

  @Secured({"ROLE_ADMIN", "ROLE_CLIENT"})
  @PostMapping("/pipeline/recommendation")
  public ResponseEntity<?> findPipelineRecommendation(
      @RequestBody PipelineRecommendationRequest request) {
    return ResponseEntity.ok(pipelineService.generatePipelineRecommendation(request));
  }
}
