package com.dovit.backend.config;

import com.dovit.backend.domain.*;
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
    modelMapper
        .getConfiguration()
        .setDeepCopyEnabled(false)
        .setSkipNullEnabled(true)
        .setSkipNullEnabled(true);
    modelMapper.addMappings(this.toolResponsePropertyMap(BASE_IMAGE_URL));
    modelMapper.addMappings(this.projectResponsePropertyMap(BASE_IMAGE_URL));
    modelMapper.addMappings(this.auditResponsePropertyMap());
    return modelMapper;
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

  private PropertyMap<Member, MemberResponse> memberResponsePropertyMap(String BASE_IMAGE_URL) {
    Converter<List<ToolProfile>, List<ToolProfileResponse>> converter =
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

    return new PropertyMap<Member, MemberResponse>() {
      @Override
      protected void configure() {
        using(converter).map(source.getToolProfile()).setTools(new ArrayList<>());
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
