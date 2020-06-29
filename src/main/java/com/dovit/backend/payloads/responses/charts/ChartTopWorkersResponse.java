package com.dovit.backend.payloads.responses.charts;

import lombok.Builder;
import lombok.Data;

/**
 * @author Ramón París
 * @since 28-06-20
 */
@Data
@Builder
public class ChartTopWorkersResponse {

  private String id;
  private String fullName;
  private int activeProjectsQty;
  private int totalQty;
}
