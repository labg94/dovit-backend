package com.dovit.backend.payloads.responses.charts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 29-06-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChartLicenseConflict {

  private Long categoryId;
  private String category;
  private int qty;
  private String message;
  private String tools;
}
