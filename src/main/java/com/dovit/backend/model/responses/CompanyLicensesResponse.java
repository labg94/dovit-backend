package com.dovit.backend.model.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ramón París
 * @since 04-10-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyLicensesResponse {

  private Long id;
  private String startDate;
  private String expirationDate;

  private Long licenseId;
  private String licenseName;
  private String licensePayCycle;
  private String licenseObservation;
  private String licenseTypeId;
  private String licenseTypeDescription;

  private String licenseToolId;
  private String licenseToolName;
  private String licenseToolImageUrl;

  private List<LicensePricingResponse> licensePrices;
}
