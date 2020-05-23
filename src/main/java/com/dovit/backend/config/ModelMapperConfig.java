package com.dovit.backend.config;

import com.dovit.backend.domain.*;
import com.dovit.backend.model.requests.LicenseRequest;
import com.dovit.backend.model.requests.ProjectMemberRequest;
import com.dovit.backend.model.requests.ProjectRequest;
import com.dovit.backend.model.requests.ToolRequest;
import com.dovit.backend.model.responses.*;
import com.dovit.backend.util.DateUtil;
import com.google.gson.Gson;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Ramón París
 * @since 29-03-20
 */
@Configuration
public class ModelMapperConfig {

  private final Gson gson = new Gson();

  @Value("${api.image.route}")
  private String BASE_IMAGE_URL;

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setDeepCopyEnabled(false).setSkipNullEnabled(true);

    // Add response mappers
    modelMapper.addMappings(this.toolResponsePropertyMap(BASE_IMAGE_URL));
    modelMapper.addMappings(this.projectResponsePropertyMap(BASE_IMAGE_URL));
    modelMapper.addMappings(this.auditResponsePropertyMap());
    modelMapper.addMappings(this.memberResponsePropertyMap(BASE_IMAGE_URL));
    modelMapper.addMappings(this.projectMemberResumePropertyMap());

    // Add requests mappers
    modelMapper.addMappings(projectRequestPropertyMap());
    modelMapper.addMappings(licensePropertyMap());
    modelMapper.addMappings(toolPropertyMap());

    return modelMapper;
  }

  private PropertyMap<ProjectRequest, Project> projectRequestPropertyMap() {
    Converter<List<ProjectMemberRequest>, List<ProjectMember>> memberConverter =
        mappingContext ->
            mappingContext.getSource().stream()
                .map(
                    m ->
                        ProjectMember.builder()
                            .memberId(m.getMemberId())
                            .devOpsCategoryId(m.getDevOpsCategoryId())
                            .build())
                .collect(Collectors.toList());

    Converter<Long, Company> companyConverter =
        mappingContext -> Company.builder().id(mappingContext.getSource()).build();

    return new PropertyMap<ProjectRequest, Project>() {
      @Override
      protected void configure() {
        using(memberConverter).map(source.getMembers()).setMembers(Collections.emptyList());
        using(companyConverter).map(source.getCompanyId()).setCompany(new Company());
      }
    };
  }

  private PropertyMap<ToolRequest, Tool> toolPropertyMap() {
    Converter<List<Long>, List<DevOpsSubcategory>> converter =
        mappingContext ->
            mappingContext.getSource().stream()
                .map(id -> DevOpsSubcategory.builder().id(id).build())
                .collect(Collectors.toList());

    Converter<String, String> imageUrlConverter =
        mappingContext -> "/" + mappingContext.getSource().toLowerCase();

    return new PropertyMap<>() {
      @Override
      protected void configure() {
        using(converter).map(source.getSubcategoryIds()).setSubcategories(new ArrayList<>());
        map("").setCreatedAt(LocalDateTime.now());
        map("").setUpdatedAt(LocalDateTime.now());
        using(imageUrlConverter).map(source.getName()).setImageUrl("");
        map("").setToolProfile(new ArrayList<>());
      }
    };
  }

  private PropertyMap<LicenseRequest, License> licensePropertyMap() {
    Converter<Long, LicensePayCycle> payCycleConverter =
        mappingContext -> LicensePayCycle.builder().id(mappingContext.getSource()).build();

    Converter<Long, LicenseType> licenseTypeConverter =
        mappingContext -> LicenseType.builder().id(mappingContext.getSource()).build();

    return new PropertyMap<>() {
      @Override
      protected void configure() {
        using(payCycleConverter)
            .map(source.getLicensePayCycleId())
            .setPayCycle(new LicensePayCycle());

        map("").setCreatedAt(null);
        map("").setUpdatedAt(null);
        map("").setCompanyLicenses(new ArrayList<>());
        map(source).setTool(new Tool());
        using(licenseTypeConverter)
            .map(source.getLicenseTypeId())
            .setLicenseType(new LicenseType());
      }
    };
  }

  private PropertyMap<Audit, AuditResponse> auditResponsePropertyMap() {
    Converter<User, String> fullNameConverter =
        mappingContext ->
            mappingContext.getSource() != null
                ? mappingContext.getSource().getName()
                    + " "
                    + mappingContext.getSource().getLastName()
                : null;

    Converter<LocalDateTime, String> dateConverter =
        mappingContext -> DateUtil.formatDateToString(mappingContext.getSource());

    Converter<Object, String> jsonConverter =
        mappingContext -> gson.toJson(mappingContext.getSource());

    return new PropertyMap<>() {
      @Override
      protected void configure() {
        using(fullNameConverter).map(source.getUser()).setUserFullName("");
        using(dateConverter).map(source.getActionDate()).setActionDate("");
        using(jsonConverter).map(source.getData()).setData("");
      }
    };
  }

  private PropertyMap<Member, MemberResponseDetail> memberResponsePropertyMap(
      String BASE_IMAGE_URL) {
    Converter<List<ToolProfile>, List<ToolProfileResponse>> toolConverter =
        mappingContext ->
            mappingContext.getSource().stream()
                .map(
                    t ->
                        ToolProfileResponse.builder()
                            .toolId(t.getToolId())
                            .toolName(t.getTool().getName())
                            .levelId(t.getLevelId())
                            .levelDesc(t.getLevel().getDescription())
                            .imageUrl(BASE_IMAGE_URL + t.getTool().getImageUrl())
                            .build())
                .collect(Collectors.toList());

    Converter<List<ProjectMember>, Long> activeProjectsQtyConverter =
        mappingContext ->
            (long)
                mappingContext.getSource().stream()
                    .filter(projectMember -> !projectMember.getProject().getFinished())
                    .count();

    Converter<List<ProjectMember>, String> availableStatusDescriptionConverter =
        mappingContext -> {
          final long totalActive =
              mappingContext.getSource().stream()
                  .filter(projectMember -> !projectMember.getProject().getFinished())
                  .count();

          if (totalActive == 0L) {
            return "Available";
          } else if (totalActive == 1L) {
            return "Partially Available";
          } else {
            return "Busy";
          }
        };

    Converter<List<ProjectMember>, Long> availableStatusIdConverter =
        mappingContext -> {
          final long totalActive =
              mappingContext.getSource().stream()
                  .filter(projectMember -> !projectMember.getProject().getFinished())
                  .count();

          if (totalActive == 0L) {
            return 1L;
          } else if (totalActive == 1L) {
            return 2L;
          } else {
            return 3L;
          }
        };

    Converter<List<ProjectMember>, Long> allProjectsQtyConverter =
        mappingContext -> (long) mappingContext.getSource().size();

    return new PropertyMap<>() {
      @Override
      protected void configure() {
        using(toolConverter).map(source.getToolProfile()).setTools(new ArrayList<>());
        using(allProjectsQtyConverter).map(source.getProjects()).setAllProjectsQty(1L);
        using(activeProjectsQtyConverter).map(source.getProjects()).setActiveProjectsQty(1L);
        using(availableStatusDescriptionConverter)
            .map(source.getProjects())
            .setAvailableStatusDescription("");

        using(availableStatusIdConverter).map(source.getProjects()).setAvailableStatusId(1L);
      }
    };
  }

  private PropertyMap<ProjectMember, ProjectMemberResume> projectMemberResumePropertyMap() {
    Converter<ProjectMember, List<String>> categoriesConverter =
        mappingContext ->
            Collections.singletonList(
                mappingContext.getSource().getDevOpsCategories().getDescription());
    return new PropertyMap<>() {
      @Override
      protected void configure() {
        map(source.getProject().getId()).setProjectId(1L);
        using(categoriesConverter).map(source).setCategoriesParticipation(new ArrayList<>());
      }
    };
  }

  private ToolProfileResponse createToolProfile(ToolProfile toolProfile, String BASE_IMAGE_URL) {
    return ToolProfileResponse.builder()
        .toolId(toolProfile.getToolId())
        .toolName(toolProfile.getTool().getName())
        .levelId(toolProfile.getLevelId())
        .levelDesc(toolProfile.getLevel().getDescription())
        .imageUrl(BASE_IMAGE_URL + toolProfile.getTool().getImageUrl())
        .build();
  }

  /** Property map used in ProjectMember - ProjectMemberResponse mapping */
  private PropertyMap<Project, ProjectResponse> projectResponsePropertyMap(String BASE_IMAGE_URL) {
    Converter<List<ProjectMember>, List<ProjectMemberResponse>> memberConverter =
        mappingContext ->
            mappingContext.getSource().stream()
                .collect(Collectors.groupingBy(ProjectMember::getMember))
                .entrySet()
                .stream()
                .map(
                    entrySet -> {
                      Member member = entrySet.getKey();
                      List<ProjectMember> memberValues = entrySet.getValue();
                      return ProjectMemberResponse.builder()
                          .memberId(member.getId())
                          .memberName(member.getName())
                          .memberLastName(member.getLastName())
                          .participation(
                              memberValues.stream()
                                  .map(
                                      memberValue ->
                                          MemberParticipationResponse.builder()
                                              .devopsCategoryName(
                                                  memberValue
                                                      .getDevOpsCategories()
                                                      .getDescription())
                                              .tools(
                                                  member.getToolProfile().stream()
                                                      .filter(
                                                          toolProfile ->
                                                              toolProfile.getTool()
                                                                  .getSubcategories().stream()
                                                                  .collect(
                                                                      Collectors.groupingBy(
                                                                          sub ->
                                                                              sub.getDevOpsCategory()
                                                                                  .getId()))
                                                                  .containsKey(
                                                                      memberValue
                                                                          .getDevOpsCategoryId()))
                                                      .map(
                                                          tool ->
                                                              this.createToolProfile(
                                                                  tool, BASE_IMAGE_URL))
                                                      .collect(Collectors.toList()))
                                              .build())
                                  .collect(Collectors.toList()))
                          .build();
                    })
                .collect(Collectors.toList());

    Converter<LocalDate, String> dateConverter =
        mappingContext -> DateUtil.formatDateToString(mappingContext.getSource());

    return new PropertyMap<>() {
      @Override
      protected void configure() {
        using(memberConverter).map(source.getMembers()).setMembers(new ArrayList<>());
        using(dateConverter).map(source.getStart()).setStart("");
      }
    };
  }

  /** Property Map used to map DevOpsSubcategory and DevOpsCategory into tags */
  private PropertyMap<Tool, ToolResponse> toolResponsePropertyMap(String BASE_IMAGE_URL) {
    Converter<List<DevOpsSubcategory>, List<String>> converterTags =
        mappingContext -> {
          List<String> tags =
              mappingContext.getSource().stream()
                  .map(DevOpsSubcategory::getDescription)
                  .collect(Collectors.toList());

          List<String> parentTags =
              mappingContext.getSource().stream()
                  .map(DevOpsSubcategory::getDevOpsCategory)
                  .map(DevOpsCategory::getDescription)
                  .distinct()
                  .collect(Collectors.toList());

          return Stream.concat(parentTags.stream(), tags.stream()).collect(Collectors.toList());
        };

    Converter<String, String> convertImgUrl =
        mappingContext -> BASE_IMAGE_URL + mappingContext.getSource();

    return new PropertyMap<>() {
      @Override
      protected void configure() {
        using(converterTags).map(source.getSubcategories()).setTags(new ArrayList<>());
        using(convertImgUrl).map(source.getImageUrl()).setImageUrl(BASE_IMAGE_URL);
      }
    };
  }
}
