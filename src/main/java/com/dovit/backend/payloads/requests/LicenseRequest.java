package com.dovit.backend.payloads.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
  private Long toolId;

  @NotEmpty(message = "License name is a mandatory field")
  private String licenseName;

  @NotNull(message = "License pay cycle is a mandatory field")
  private Long licensePayCycleId;

  private String licenseObservation;

  @NotNull(message = "License type is a mandatory field")
  private Long licenseTypeId;

  @Nullable
  @Size(min = 1, message = "Prices cannot be empty")
  private List<LicensePricingRequest> licensePrices;
}
