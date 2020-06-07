package com.dovit.backend.services;

import com.dovit.backend.payloads.requests.PipelineRecommendationRequest;
import com.dovit.backend.payloads.responses.PipelineResponse;

import java.util.List;

/**
 * @author Ramón París
 * @since 31-05-20
 */
public interface PipelineService {

  PipelineResponse generatePipelineRecommendation(PipelineRecommendationRequest request);

  List<PipelineResponse> findAllByProjectId(Long projectId);
}
