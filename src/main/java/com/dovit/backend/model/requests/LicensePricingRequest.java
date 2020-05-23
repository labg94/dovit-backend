package com.dovit.backend.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author Ramón París
 * @since 23-05-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LicensePricingRequest {

  private Long id;

  @NotNull(message = "minimum users is a mandatory field")
  private Long minUsers;

  @NotNull(message = "maximum users is a mandatory field")
  private Long maxUsers;

  @NotNull(message = "price is a mandatory field")
  private Double price;
}
