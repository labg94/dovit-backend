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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChartLicenseResponse {

  private Long licenseId;
  private String licenseName;
  private Long licenseToolId;
  private String licenseToolName;
  private String licensePayCycleDescription;
  private String startDate;
  private String expirationDate;
}
