package com.dovit.backend.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ramón París
 * @since 14-06-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SelectedPipelineResponse {

  private Long pipelineId;
  private Double cost;
  private List<PipelineToolResponse> tools;
}
