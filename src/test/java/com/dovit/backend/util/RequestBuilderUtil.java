package com.dovit.backend.util;

import com.dovit.backend.model.requests.CompanyLicenseRequest;

import java.time.LocalDate;

/**
 * @author Ramón París
 * @since 12-04-20
 */
public class RequestBuilderUtil {

  public static CompanyLicenseRequest companyLicenseRequest =
      CompanyLicenseRequest.builder()
          .id(1L)
          .licenseId(1L)
          .companyId(1L)
          .companyId(1L)
          .startDate(LocalDate.now())
          .expirationDate(LocalDate.now())
          .build();
}
