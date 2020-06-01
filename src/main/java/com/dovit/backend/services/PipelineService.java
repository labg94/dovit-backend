package com.dovit.backend.services;

import com.dovit.backend.model.requests.PipelineRecommendationRequest;
import com.dovit.backend.model.responses.PipelineResponse;

import java.util.List;

/**
 * @author Ramón París
 * @since 31-05-20
 */
public interface PipelineService {

  List<PipelineResponse> generatePipelineRecommendation(PipelineRecommendationRequest request);

  List<PipelineResponse> findAllByProjectId(Long projectId);
}
