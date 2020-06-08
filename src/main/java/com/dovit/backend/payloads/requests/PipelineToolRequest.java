package com.dovit.backend.payloads.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 07-06-20
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PipelineToolRequest {
  private Long toolId;
  private Long categoryId;
}
