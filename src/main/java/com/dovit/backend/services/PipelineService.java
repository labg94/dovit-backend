package com.dovit.backend.services;

import com.dovit.backend.domain.Pipeline;
import com.dovit.backend.domain.Project;
import com.dovit.backend.payloads.requests.PipelineRecommendationRequest;
import com.dovit.backend.payloads.requests.PipelineToolRequest;
import com.dovit.backend.payloads.requests.ProjectRequest;
import com.dovit.backend.payloads.responses.PipelineRecommendationResponse;

import java.util.List;

/**
 * @author Ramón París
 * @since 31-05-20
 */
public interface PipelineService {

  PipelineRecommendationResponse generatePipelineRecommendation(
      PipelineRecommendationRequest request);

  Pipeline createSelectedPipeline(Project request, List<PipelineToolRequest> selectedTools);

  Pipeline createRecommendedPipeline(Project project, ProjectRequest request);

  void updateRecommendedPipeline(Project project);
}
