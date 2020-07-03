package com.dovit.backend.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 14-06-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectPipelineResponse {

  private PipelineRecommendationResponse recommendation;
  private SelectedPipelineResponse selected;
}
