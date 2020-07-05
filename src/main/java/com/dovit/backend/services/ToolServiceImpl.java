package com.dovit.backend.services;

import com.dovit.backend.domain.DevOpsSubcategory;
import com.dovit.backend.domain.Tool;
import com.dovit.backend.domain.ToolProfile;
import com.dovit.backend.domain.ToolProjectType;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.RecommendationPointsDTO;
import com.dovit.backend.model.ToolRecommendationDTO;
import com.dovit.backend.payloads.requests.ProjectMemberRequest;
import com.dovit.backend.payloads.requests.ToolRequest;
import com.dovit.backend.payloads.responses.CategoryResponse;
import com.dovit.backend.payloads.responses.CategoryToolResponse;
import com.dovit.backend.payloads.responses.ToolResponse;
import com.dovit.backend.repositories.ToolProfileRepository;
import com.dovit.backend.repositories.ToolProjectTypeRepository;
import com.dovit.backend.repositories.ToolRepository;
import com.dovit.backend.util.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.dovit.backend.util.Constants.*;

/**
 * @author Ramón París
 * @since 01-10-2019
 */
@Service
@RequiredArgsConstructor
public class ToolServiceImpl implements ToolService {

  private final ToolRepository toolRepository;
  private final ValidatorUtil validatorUtil;
  private final ModelMapper modelMapper;
  private final DevOpsSubCategoryService subCategoryService;
  private final ToolProfileRepository toolProfileRepository;
  private final ToolProjectTypeRepository toolProjectTypeRepository;
  private final DevOpsCategoryService categoryService;

  @Override
  @Transactional
  public List<ToolResponse> findAllToolsOfCompany(Long companyId) {
    validatorUtil.canActOnCompany(companyId);
    List<Tool> tools = toolRepository.findAllByCompanyId(companyId);
    return tools.stream()
        .map(tool -> modelMapper.map(tool, ToolResponse.class))
        .sorted(Comparator.comparing(ToolResponse::getToolId))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public List<ToolResponse> findAllTools() {
    List<Tool> tools = toolRepository.findAll();
    return tools.stream()
        .map(tool -> modelMapper.map(tool, ToolResponse.class))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public ToolResponse findById(Long toolId) {
    Tool t = findToolById(toolId);

    return modelMapper.map(t, ToolResponse.class);
  }

  @Override
  @Transactional
  public Tool save(ToolRequest request) {
    request.setId(null);
    request
        .getLicenses()
        .forEach(
            l -> {
              l.setLicenseId(null);
              l.getLicensePrices()
                  .forEach(licensePricingRequest -> licensePricingRequest.setId(null));
            });

    final Tool tool = modelMapper.map(request, Tool.class);
    tool.setActive(true);
    tool.getLicenses()
        .forEach(
            license -> {
              license.setTool(tool);
              license.setActive(true);
              license
                  .getLicensePrices()
                  .forEach(licensePricing -> licensePricing.setLicense(license));
            });
    Tool response = toolRepository.save(tool);

    List<ToolProjectType> toolProjectTypes =
        request.getProjectTypeIds().stream()
            .map(
                projectTypeId ->
                    ToolProjectType.builder()
                        .projectTypeId(projectTypeId)
                        .toolId(response.getId())
                        .build())
            .collect(Collectors.toList());
    toolProjectTypeRepository.saveAll(toolProjectTypes);
    return response;
  }

  @Override
  @Transactional
  public Tool update(ToolRequest request) {
    Tool tool = findToolById(request.getId());

    // Should not update licenses
    request.setLicenses(null);
    modelMapper.map(request, tool);
    return toolRepository.save(tool);
  }

  @Override
  public void toggleActive(Long toolId) {
    Tool tool = findToolById(toolId);
    tool.setActive(!tool.isActive());
    toolRepository.save(tool);
  }

  @Override
  @Transactional
  public List<ToolResponse> findAllActiveTools() {
    List<Tool> tools = toolRepository.findAllByActive(true);
    return tools.stream()
        .map(tool -> modelMapper.map(tool, ToolResponse.class))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public List<ToolResponse> findAllActiveBySubcategory(Long subcategoryId) {
    DevOpsSubcategory subcategory = subCategoryService.findById(subcategoryId);
    List<Tool> tools = toolRepository.findAllByActiveAndSubcategoriesContains(true, subcategory);
    return tools.stream()
        .map(tool -> modelMapper.map(tool, ToolResponse.class))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public List<ToolResponse> findAllBySubcategory(Long subcategoryId) {
    DevOpsSubcategory subcategory = subCategoryService.findById(subcategoryId);
    List<Tool> tools = toolRepository.findAllBySubcategoriesContains(subcategory);
    return tools.stream()
        .map(tool -> modelMapper.map(tool, ToolResponse.class))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public List<ToolRecommendationDTO> findRecommendationByMembers(
      Long categoryId, List<ProjectMemberRequest> projectMembers) {

    List<Long> memberIds =
        projectMembers.stream()
            .filter(member -> member.getDevOpsCategoryId().equals(categoryId))
            .map(ProjectMemberRequest::getMemberId)
            .collect(Collectors.toList());

    List<ToolProfile> toolProfiles =
        toolProfileRepository.findRecommendationByMembers(categoryId, memberIds);

    return toolProfiles.stream()
        .collect(Collectors.groupingBy(ToolProfile::getToolId))
        .values()
        .stream()
        .map(
            value -> {
              ToolProfile maxLevelToolProfile =
                  value.stream()
                      .filter(toolProfile -> toolProfile.getLevel() != null)
                      .max(Comparator.comparing(toolProfile -> toolProfile.getLevel().getPoints()))
                      .orElseThrow();

              return modelMapper
                  .map(maxLevelToolProfile.getTool(), ToolRecommendationDTO.class)
                  .toBuilder()
                  .points(
                      Collections.singletonList(
                          RecommendationPointsDTO.builder()
                              .category(
                                  this.findMemberPointsTxt(
                                      maxLevelToolProfile.getLevel().getPoints()))
                              .points(maxLevelToolProfile.getLevel().getPoints())
                              .build()))
                  .build();
            })
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public List<ToolRecommendationDTO> findRecommendationByLicense(Long companyId, Long categoryId) {
    List<Tool> tools =
        toolRepository.findRecommendationByCompanyLicense(companyId, categoryId, LocalDate.now());

    return createToolRecommendation(tools)
        .map(
            recommendation ->
                recommendation.toBuilder()
                    .points(
                        Collections.singletonList(
                            RecommendationPointsDTO.builder()
                                .category(TOOL_POINTS_LICENSE_TXT)
                                .points(TOOL_POINTS_LICENSE)
                                .build()))
                    .build())
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public List<ToolRecommendationDTO> findRecommendationByProjectType(
      Long categoryId, List<Long> projectTypeIds) {
    List<ToolProjectType> toolProjectTypes =
        toolProjectTypeRepository.findRecommendationByProjectType(categoryId, projectTypeIds);

    return getToolRecommendationDTOS(toolProjectTypes);
  }

  private List<ToolRecommendationDTO> getToolRecommendationDTOS(
      List<ToolProjectType> toolProjectTypes) {
    return toolProjectTypes.stream()
        .collect(Collectors.groupingBy(ToolProjectType::getTool))
        .entrySet()
        .stream()
        .map(
            entry -> {
              Tool tool = entry.getKey();
              List<RecommendationPointsDTO> points =
                  entry.getValue().stream()
                      .map(
                          toolProjectType ->
                              RecommendationPointsDTO.builder()
                                  .points(TOOL_POINTS_PROJECT_TYPE)
                                  .category(
                                      String.format(
                                          TOOL_POINTS_PROJECT_TYPE_TXT,
                                          toolProjectType.getProjectType().getDescription()))
                                  .build())
                      .collect(Collectors.toList());
              return modelMapper.map(tool, ToolRecommendationDTO.class).toBuilder()
                  .points(points)
                  .totalPoints(
                      points.stream()
                          .map(RecommendationPointsDTO::getPoints)
                          .reduce(0, Integer::sum))
                  .build();
            })
        .sorted(Comparator.comparing(ToolRecommendationDTO::getTotalPoints).reversed())
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public List<ToolRecommendationDTO> findRecommendationByProjectHistory(
      Long categoryId, Long companyId) {
    List<Tool> tools = toolRepository.findRecommendationByProjectHistory(categoryId, companyId);
    return createToolRecommendation(tools)
        .map(
            recommendation ->
                recommendation.toBuilder()
                    .points(
                        Collections.singletonList(
                            RecommendationPointsDTO.builder()
                                .category(TOOL_POINTS_HISTORY_TXT)
                                .points(TOOL_POINTS_HISTORY)
                                .build()))
                    .build())
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public List<CategoryToolResponse> findAllByProjectTypes(List<Long> projectTypes) {
    final List<CategoryResponse> categories = categoryService.findAllActives();
    return categories.stream()
        .map(category -> modelMapper.map(category, CategoryToolResponse.class))
        .peek(
            category -> {
              final List<ToolProjectType> toolProjects =
                  toolProjectTypeRepository.findRecommendationByProjectType(
                      category.getCategoryId(), projectTypes);

              category.setTools(getToolRecommendationDTOS(toolProjects));
            })
        .sorted(Comparator.comparing(CategoryToolResponse::getCategoryId))
        .collect(Collectors.toList());
  }

  private Tool findToolById(Long toolId) {
    return toolRepository
        .findById(toolId)
        .orElseThrow(() -> new ResourceNotFoundException("Tool", "id", toolId));
  }

  private Stream<ToolRecommendationDTO> createToolRecommendation(List<Tool> tools) {
    return tools.stream().map(tool -> modelMapper.map(tool, ToolRecommendationDTO.class));
  }

  // Este código se debiese mejorar... pero qué ladilla
  private String findMemberPointsTxt(int point) {
    switch (point) {
      case 3:
        return TOOL_POINTS_MEMBER_SENIOR_TXT;
      case 2:
        return TOOL_POINTS_MEMBER_SEMI_SENIOR_TXT;
      case 1:
        return TOOL_POINTS_MEMBER_JUNIOR_TXT;
      default:
        return null;
    }
  }
}
