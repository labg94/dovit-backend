package com.dovit.backend.util;

import com.dovit.backend.domain.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Ramón París
 * @since 12-04-20
 */
public class DomainBuilderUtil {

  public static Role role = Role.builder().id(1L).name(RoleName.ROLE_ADMIN).build();
  public static Profile profile = Profile.builder().id(1L).description("Backend Developer").build();
  public static Level level = Level.builder().levelId(1L).description("Senior").build();

  public static DevOpsCategory devOpsCategoryPlanning =
      DevOpsCategory.builder().id(1L).description("Planning").active(true).build();

  public static DevOpsCategory devOpsCategoryCICD =
      DevOpsCategory.builder().id(3L).description("CI/CD").active(true).build();

  public static DevOpsSubcategory issue_tracking =
      DevOpsSubcategory.builder()
          .id(1L)
          .description("Issue Tracking")
          .devOpsCategory(devOpsCategoryPlanning)
          .build();

  public static DevOpsSubcategory repositories =
      DevOpsSubcategory.builder()
          .id(3L)
          .description("Repositories")
          .devOpsCategory(devOpsCategoryCICD)
          .build();

  public static DevOpsSubcategory CI =
      DevOpsSubcategory.builder()
          .id(4L)
          .description("Continuous Integration")
          .devOpsCategory(devOpsCategoryCICD)
          .build();

  public static DevOpsSubcategory CD =
      DevOpsSubcategory.builder()
          .id(5L)
          .description("Continuous Deployment")
          .devOpsCategory(devOpsCategoryCICD)
          .build();

  public static Tool tool =
      Tool.builder()
          .id(1L)
          .imageUrl("/url")
          .name("Gitlab")
          .subcategories(Arrays.asList(issue_tracking, repositories, CI, CD))
          .build();

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
          .expirationDate(LocalDate.now())
          .license(license)
          .company(company)
          .build();

  public static ToolProfile toolProfile =
      ToolProfile.builder().level(level).levelId(1L).tool(tool).toolId(1L).build();

  public static Member member =
      Member.builder()
          .id(1L)
          .active(true)
          .company(company)
          .name("Pepe")
          .lastName("Lota")
          .profiles(Collections.singletonList(profile))
          .toolProfile(Collections.singletonList(toolProfile))
          .build();

  public static ProjectMember projectMember =
      ProjectMember.builder()
          .member(member)
          .memberId(1L)
          .devOpsCategories(devOpsCategoryPlanning)
          .devOpsCategoryId(1L)
          .build();

  public static Project project =
      Project.builder()
          .company(company)
          .finished(false)
          .id(1L)
          .name("Dov IT")
          .observation("Project!")
          .start(LocalDate.now())
          .members(Collections.singletonList(projectMember))
          .build();
}
