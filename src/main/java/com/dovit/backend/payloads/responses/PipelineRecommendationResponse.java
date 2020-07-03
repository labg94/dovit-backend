package com.dovit.backend.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ramón París
 * @since 31-05-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PipelineRecommendationResponse {

  private Long id;
  private Double cost;
  private List<CategoryRecommendationResponse> pipelineTools;
}
