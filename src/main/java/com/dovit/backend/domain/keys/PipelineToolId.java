package com.dovit.backend.domain.keys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Ramón París
 * @since 07-06-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PipelineToolId implements Serializable {

  private Long pipelineId;
  private Long toolId;
  private Long categoryId;
}
