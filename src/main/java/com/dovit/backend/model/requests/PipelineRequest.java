package com.dovit.backend.model.requests;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PipelineRequest {

  private Long pipelineId;
  private List<Long> toolIds;
}
