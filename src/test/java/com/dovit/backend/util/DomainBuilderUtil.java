package com.dovit.backend.util;

import com.dovit.backend.domain.*;

import java.time.LocalDate;

/**
 * @author Ramón París
 * @since 12-04-20
 */
public class DomainBuilderUtil {

  public static Role role = Role.builder().id(1L).name(RoleName.ROLE_ADMIN).build();
  public static Profile profile = Profile.builder().id(1L).description("Backend Developer").build();
  public static Level level = Level.builder().levelId(1L).description("Senior").build();

  public static Tool tool = Tool.builder().id(1L).imageUrl("/url").name("Gitlab").build();

  public static LicenseType licenseType =
      LicenseType.builder().id(1L).description("Premium").build();

  public static License license =
      License.builder()
          .id(1L)
          .name("Basic plan")
          .observation("Observation")
          .payCycle("Monthly")
          .licenseType(licenseType)
          .tool(tool)
          .build();

  public static Company company = Company.builder().id(1L).name("Clever IT").build();

  public static CompanyLicense companyLicense =
      CompanyLicense.builder()
          .id(1L)
          .startDate(LocalDate.of(2020, 11, 10))
          .license(license)
          .company(company)
          .build();
}
