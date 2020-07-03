package com.dovit.backend.payloads.responses.charts;

import lombok.Builder;
import lombok.Data;

/**
 * @author Ramón París
 * @since 29-06-20
 */
@Data
@Builder
public class ChartTopToolsByProject {

  private Long toolId;
  private String toolName;
  private Long value;
}
