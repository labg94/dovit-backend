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
public class PipelineResponse {

  private Long id;
  private boolean recommended;
  private Double cost;
  private List<PipelineToolResponse> pipelineTools;
}
