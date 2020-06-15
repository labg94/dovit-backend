package com.dovit.backend.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 14-06-20
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PipelineToolResponse {

  private Long toolId;
  private String toolName;
  private String toolDescription;
  private String toolImageUrl;
  private Long categoryId;
  private String categoryDescription;
}
