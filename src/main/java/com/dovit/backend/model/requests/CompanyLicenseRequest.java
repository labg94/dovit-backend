package com.dovit.backend.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author Ramón París
 * @since 15-10-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyLicenseRequest {

  private Long id;

  @NotNull(message = "Remember to select a company")
  private Long companyId;

  @NotNull(message = "Remember to select a license")
  private Long licenseId;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @NotNull(message = "Remember to select a start date")
  private LocalDate startDate;

  private LocalDate expirationDate;
}
