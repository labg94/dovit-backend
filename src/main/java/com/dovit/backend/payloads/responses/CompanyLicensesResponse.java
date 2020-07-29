package com.dovit.backend.payloads.responses;

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
  private String licensePayCycleDescription;
  private String licensePayCycleId;
  private String licenseObservation;
  private String licenseTypeId;
  private String licenseTypeDescription;

  private String licenseToolId;
  private String licenseToolName;
  private String licenseToolImageUrl;

  private List<LicensePricingResponse> licensePrices;

  private boolean active;
  private List<String> tags;
  private Long membersUsingQty;
  private Double pricing;
}
