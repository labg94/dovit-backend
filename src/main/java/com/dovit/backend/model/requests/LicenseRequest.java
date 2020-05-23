package com.dovit.backend.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Ramón París
 * @since 23-05-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LicenseRequest {

  private Long licenseId;

  @NotEmpty(message = "License name is a mandatory field")
  private String licenseName;

  @NotEmpty(message = "License pay cycle is a mandatory field")
  private Long licensePayCycleId;

  private String licenseObservation;

  @NotNull(message = "License type is a mandatory field")
  private Long licenseTypeId;

  @NotNull(message = "Prices are a mandatory fields")
  private List<LicensePricingRequest> licensePrices;
}
