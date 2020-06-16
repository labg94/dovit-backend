package com.dovit.backend.config;

import com.dovit.backend.domain.*;
import com.dovit.backend.payloads.requests.LicenseRequest;
import com.dovit.backend.payloads.requests.ProjectMemberRequest;
import com.dovit.backend.payloads.requests.ProjectRequest;
import com.dovit.backend.payloads.requests.ToolRequest;
import com.dovit.backend.payloads.responses.*;
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
import java.util.Optional;
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
    modelMapper.addMappings(this.projectResponsePropertyMap());
    modelMapper.addMappings(this.auditResponsePropertyMap());
    modelMapper.addMappings(this.memberResponsePropertyMap(BASE_IMAGE_URL));
    modelMapper.addMappings(this.projectMemberResumePropertyMap());
    modelMapper.addMappings(this.licenseLicenseResponsePropertyMap());
    modelMapper.addMappings(this.pricingResponsePropertyMap());
    modelMapper.addMappings(this.suggestionResponsePropertyMap());
    modelMapper.addMappings(this.mapProjectTypeResponse());
    modelMapper.addMappings(this.pipelineToolResponsePropertyMap(BASE_IMAGE_URL));
    modelMapper.addMappings(this.categoryRecommendationResponsePropertyMap());

    // Add requests mappers
    modelMapper.addMappings(projectRequestPropertyMap());
    modelMapper.addMappings(licensePropertyMap());
    modelMapper.addMappings(toolPropertyMap());

    return modelMapper;
  }

  private PropertyMap<SuggestionMailbox, SuggestionResponse> suggestionResponsePropertyMap() {
    Converter<SuggestionMailbox, String> converterFullName =
        mappingContext -> {
          User user = mappingContext.getSource().getSuggestedBy();
          if (user != null) return String.format("%s %s", user.getName(), user.getLastName());
          else return "Some admin";
        };

    Converter<String, String> otherOptionConverter =
        mappingContext -> Optional.ofNullable(mappingContext.getSource()).orElse("Other");

    return new PropertyMap<>() {
      @Override
      protected void configure() {
        using(converterFullName).map(source).setSuggestedByFullName("");
        using(otherOptionConverter)
            .map(source.getCategory().getDescription())
            .setCategoryDescription("");
        using(otherOptionConverter)
            .map(source.getSubcategory().getDescription())
            .setSubCategoryDescription("");
      }
    };
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

    Converter<List<Long>, List<ProjectType>> projectTypeConverter =
        mappingContext ->
            mappingContext.getSource().stream()
                .map(id -> ProjectType.builder().id(id).build())
                .collect(Collectors.toList());

    Converter<Long, Company> companyConverter =
        mappingContext -> Company.builder().id(mappingContext.getSource()).build();

    return new PropertyMap<ProjectRequest, Project>() {
      @Override
      protected void configure() {
        using(memberConverter).map(source.getMembers()).setMembers(Collections.emptyList());
        using(companyConverter).map(source.getCompanyId()).setCompany(new Company());
        using(projectTypeConverter)
            .map(source.getProjectTypeIds())
            .setProjectTypes(Collections.emptyList());
      }
    };
  }

  private PropertyMap<License, LicenseResponse> licenseLicenseResponsePropertyMap() {
    return new PropertyMap<>() {
      @Override
      protected void configure() {
        map(source.getId()).setLicenseId(1L);
      }
    };
  }

  private PropertyMap<LicensePricing, LicensePricingResponse> pricingResponsePropertyMap() {
    return new PropertyMap<>() {
      @Override
      protected void configure() {
        map(source.getId()).setLicensePricingId(1L);
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
        using(imageUrlConverter).map(source.getName()).setImageUrl("");

        skip(destination.getProjectTypes());
        skip(destination.getCreatedAt());
        skip(destination.getUpdatedAt());
        skip(destination.getToolProfile());
        skip(destination.isActive());
      }
    };
  }

  private PropertyMap<LicenseRequest, License> licensePropertyMap() {
    Converter<Long, LicensePayCycle> payCycleConverter =
        mappingContext -> LicensePayCycle.builder().id(mappingContext.getSource()).build();

    Converter<Long, LicenseType> licenseTypeConverter =
        mappingContext -> LicenseType.builder().id(mappingContext.getSource()).build();

    Converter<LicenseRequest, Long> licenseIdConverter =
        mappingContext -> mappingContext.getSource().getLicenseId();

    return new PropertyMap<>() {
      @Override
      protected void configure() {
        using(payCycleConverter)
            .map(source.getLicensePayCycleId())
            .setPayCycle(new LicensePayCycle());

        using(licenseIdConverter).map(source).setId(1L);
        //        map(source.getLicenseId()).setId(1L);

        skip(destination.isActive());
        skip(destination.getCreatedAt());
        skip(destination.getUpdatedAt());
        skip(destination.getCompanyLicenses());
        skip(destination.getTool());
        map(source.getToolId()).getTool().setId(1L);
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
  private PropertyMap<Project, ProjectResponse> projectResponsePropertyMap() {
    Converter<LocalDate, String> dateConverter =
        mappingContext -> DateUtil.formatDateToString(mappingContext.getSource());

    Converter<List<ProjectMember>, List<ProjectMemberCategoryResponse>> projectMemberConverter =
        mappingContext ->
            mappingContext.getSource().stream()
                .collect(Collectors.groupingBy(ProjectMember::getDevOpsCategories))
                .entrySet()
                .stream()
                .map(
                    entry -> {
                      final DevOpsCategory category = entry.getKey();
                      final List<ProjectMemberResponse> members =
                          entry.getValue().stream()
                              .map(
                                  projectMember ->
                                      ProjectMemberResponse.builder()
                                          .memberId(projectMember.getMemberId())
                                          .memberName(projectMember.getMember().getName())
                                          .memberLastName(projectMember.getMember().getLastName())
                                          .memberProfiles(
                                              projectMember.getMember().getProfiles().stream()
                                                  .map(Profile::getDescription)
                                                  .collect(Collectors.joining(", ")))
                                          .build())
                              .collect(Collectors.toList());

                      return ProjectMemberCategoryResponse.builder()
                          .categoryId(category.getId())
                          .categoryDescription(category.getDescription())
                          .members(members)
                          .build();
                    })
                .collect(Collectors.toList());

    return new PropertyMap<>() {
      @Override
      protected void configure() {
        using(projectMemberConverter)
            .map(source.getMembers())
            .setCategoryMembers(Collections.emptyList());
        using(dateConverter).map(source.getStart()).setStart("");
        using(dateConverter).map(source.getEndDate()).setEndDate("");
      }
    };
  }

  private PropertyMap<PipelineTool, PipelineToolResponse> pipelineToolResponsePropertyMap(
      String BASE_IMAGE_URL) {

    Converter<String, String> convertImgUrl =
        mappingContext -> BASE_IMAGE_URL + mappingContext.getSource();

    return new PropertyMap<>() {
      @Override
      protected void configure() {
        map(source.getCategoryId()).setCategoryId(1L);
        map(source.getToolId()).setToolId(1L);
        using(convertImgUrl).map(source.getTool().getImageUrl()).setToolImageUrl(BASE_IMAGE_URL);
      }
    };
  }

  private PropertyMap<Pipeline, PipelineRecommendationResponse>
      categoryRecommendationResponsePropertyMap() {
    return new PropertyMap<>() {
      final Gson gson = new Gson();
      final Converter<List<PipelineTool>, List<CategoryRecommendationResponse>> converter =
          mappingContext ->
              mappingContext.getSource().stream()
                  .map(pipelineTool -> gson.toJson(pipelineTool.getLog()))
                  .map(json -> gson.fromJson(json, CategoryRecommendationResponse.class))
                  .collect(Collectors.toList());

      @Override
      protected void configure() {
        using(converter).map(source.getPipelineTools()).setPipelineTools(Collections.emptyList());
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

  private PropertyMap<ToolProjectType, ProjectTypeResponse> mapProjectTypeResponse() {
    return new PropertyMap<>() {
      @Override
      protected void configure() {
        map(source.getProjectType().getId()).setProjectTypeId(1L);
      }
    };
  }
}
