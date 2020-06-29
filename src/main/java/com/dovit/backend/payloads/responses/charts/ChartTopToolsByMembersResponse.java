package com.dovit.backend.payloads.responses.charts;

import lombok.Builder;
import lombok.Data;

/**
 * @author Ramón París
 * @since 28-06-20
 */
@Data
@Builder
public class ChartTopToolsByMembersResponse {

  private Long id;
  private String name;
  private Long value;
}
