package com.dovit.backend.services;

import com.dovit.backend.domain.*;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.RecommendationPointsDTO;
import com.dovit.backend.model.ToolRecommendationDTO;
import com.dovit.backend.payloads.requests.PipelineRecommendationRequest;
import com.dovit.backend.payloads.requests.PipelineToolRequest;
import com.dovit.backend.payloads.requests.ProjectMemberRequest;
import com.dovit.backend.payloads.requests.ProjectRequest;
import com.dovit.backend.payloads.responses.CategoryRecommendationResponse;
import com.dovit.backend.payloads.responses.PipelineRecommendationResponse;
import com.dovit.backend.repositories.DevOpsCategoryRepository;
import com.dovit.backend.repositories.PipelineRepository;
import com.dovit.backend.repositories.PipelineToolRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.dovit.backend.util.Constants.TOOL_POINTS_EXISTS;
import static com.dovit.backend.util.Constants.TOOL_POINTS_EXISTS_TXT;

/**
 * @author Ramón París
 * @since 31-05-20
 */
@Service
@RequiredArgsConstructor
public class PipelineServiceImpl implements PipelineService {

  private final PipelineRepository pipelineRepository;
  private final PipelineToolRepository pipelineToolRepository;
  private final DevOpsCategoryRepository devOpsCategoryRepository;
  private final ToolService toolService;
  private final ModelMapper modelMapper;

  @Value("${api.image.route}")
  private String BASE_IMAGE_URL;

  @Override
  @Transactional
  public PipelineRecommendationResponse generatePipelineRecommendation(
      PipelineRecommendationRequest request) {

    final List<DevOpsCategory> categories = devOpsCategoryRepository.findAllByActiveOrderById(true);

    final List<CategoryRecommendationResponse> categoriesRecommendation =
        categories.stream()
            .map(
                category -> {
                  List<ToolRecommendationDTO> toolsFromLicenses =
                      toolService.findRecommendationByLicense(
                          request.getCompanyId(), category.getId());

                  List<ToolRecommendationDTO> toolsFromMembers =
                      toolService.findRecommendationByMembers(
                          category.getId(), request.getProjectMembers());

                  List<ToolRecommendationDTO> toolsFromProjectType =
                      toolService.findRecommendationByProjectType(
                          category.getId(), request.getProjectTypeIds());

                  List<ToolRecommendationDTO> toolsFromHistory =
                      toolService.findRecommendationByProjectHistory(
                          category.getId(), request.getCompanyId());

                  // We join all the recommendations gave by each set an grouped by tool Id
                  final Map<Long, List<ToolRecommendationDTO>> groupedTools =
                      Stream.of(
                              toolsFromLicenses,
                              toolsFromMembers,
                              toolsFromProjectType,
                              toolsFromHistory)
                          .flatMap(Collection::stream)
                          .collect(Collectors.groupingBy(ToolRecommendationDTO::getToolId));

                  // Work on recommendations grouped by tool. This is to join the points of each set
                  final List<ToolRecommendationDTO> recommendations =
                      groupedTools.values().stream()
                          .map(
                              toolRecommendationDTOS -> {
                                // Take the current tool
                                ToolRecommendationDTO currentTool =
                                    toolRecommendationDTOS.stream()
                                        .findFirst()
                                        .orElseThrow(
                                            () -> new ResourceNotFoundException("", "", ""));

                                // Join all the points gave in each set
                                final List<RecommendationPointsDTO> points =
                                    toolRecommendationDTOS.stream()
                                        .map(ToolRecommendationDTO::getPoints)
                                        .flatMap(Collection::stream)
                                        .collect(Collectors.toList());

                                // build the tool recommendation with correct points
                                return currentTool.toBuilder()
                                    .points(points)
                                    .totalPoints(
                                        points.stream()
                                            .map(RecommendationPointsDTO::getPoints)
                                            .reduce(0, Integer::sum))
                                    .build();
                              })
                          .collect(Collectors.toList());

                  return CategoryRecommendationResponse.builder()
                      .categoryId(category.getId())
                      .categoryDescription(category.getDescription())
                      .allTools(recommendations)
                      .build();
                })
            .collect(Collectors.toList());

    // To know if a tool has been recommended in any other category
    categoriesRecommendation.forEach(
        category -> {
          // Find all other categories
          List<CategoryRecommendationResponse> othersCats =
              categoriesRecommendation.stream()
                  .filter(otherCat -> !otherCat.getCategoryId().equals(category.getCategoryId()))
                  .collect(Collectors.toList());

          // Evaluates each tool of the currentCategory
          category
              .getAllTools()
              .forEach(
                  currentTool ->
                      othersCats.stream()
                          .filter( // Filters all the "other categories" that have this tool
                              otherCat ->
                                  otherCat.getAllTools().stream()
                                      .map(ToolRecommendationDTO::getToolId)
                                      .collect(Collectors.toList())
                                      .contains(currentTool.getToolId()))
                          .forEach(
                              // Add points to the currentTool per each category that exists
                              otherCat -> {
                                currentTool
                                    .getPoints()
                                    .add(
                                        RecommendationPointsDTO.builder()
                                            .points(TOOL_POINTS_EXISTS)
                                            .category(
                                                String.format(
                                                    TOOL_POINTS_EXISTS_TXT,
                                                    otherCat.getCategoryDescription()))
                                            .build());

                                // Calculate total points of current category
                                currentTool.setTotalPoints(
                                    currentTool.getPoints().stream()
                                        .map(RecommendationPointsDTO::getPoints)
                                        .reduce(0, Integer::sum));
                              }));

          ToolRecommendationDTO recommendedTool =
              category.getAllTools().stream()
                  .max(Comparator.comparing(ToolRecommendationDTO::getTotalPoints))
                  .orElse(null);

          category.setRecommendedTool(recommendedTool);
          if (recommendedTool != null) {
            category.setOtherTools(
                category.getAllTools().stream()
                    .filter(Objects::nonNull)
                    .filter(r -> !r.getToolId().equals(recommendedTool.getToolId()))
                    .sorted(Comparator.comparing(ToolRecommendationDTO::getTotalPoints).reversed())
                    .collect(Collectors.toList()));
          } else { // if there are no recommendation, brings all the tools of the category
            categories.stream()
                .filter(devOpsCategory1 -> devOpsCategory1.getId().equals(category.getCategoryId()))
                .findFirst()
                .ifPresent(
                    devOpsCategory ->
                        category.setOtherTools(
                            devOpsCategory.getSubcategories().stream()
                                .map(DevOpsSubcategory::getTools)
                                .flatMap(Collection::stream)
                                .distinct()
                                .map(
                                    tool ->
                                        modelMapper
                                            .map(tool, ToolRecommendationDTO.class)
                                            .toBuilder()
                                            .totalPoints(0)
                                            .points(Collections.emptyList())
                                            .build())
                                .collect(Collectors.toList())));
          }
        });

    return PipelineRecommendationResponse.builder()
        .pipelineTools(categoriesRecommendation)
        .cost(null)
        .build();
  }

  @Override
  public Pipeline createSelectedPipeline(Project project, List<PipelineToolRequest> selectedTools) {
    final List<PipelineTool> pipelineTools =
        selectedTools.stream()
            .map(
                tool ->
                    PipelineTool.builder()
                        .toolId(tool.getToolId())
                        .categoryId(tool.getCategoryId())
                        .build())
            .collect(Collectors.toList());

    return Pipeline.builder()
        .recommended(false)
        .project(project)
        .pipelineTools(pipelineTools)
        .cost(null)
        .build();
  }

  @Override
  public Pipeline createRecommendedPipeline(Project project, ProjectRequest request) {
    final PipelineRecommendationResponse recommendation =
        this.generatePipelineRecommendation(
            PipelineRecommendationRequest.builder()
                .companyId(request.getCompanyId())
                .budget(request.getBudget())
                .projectMembers(request.getMembers())
                .projectTypeIds(request.getProjectTypeIds())
                .build());

    return Pipeline.builder()
        .recommended(true)
        .project(project)
        .pipelineTools(createPipelineToolByRecommendation(recommendation))
        .cost(recommendation.getCost())
        .build();
  }

  @Override
  @Transactional
  public void updateRecommendedPipeline(Project project) {
    PipelineRecommendationRequest recommendationRequest =
        PipelineRecommendationRequest.builder()
            .projectTypeIds(
                project.getProjectTypes().stream()
                    .map(ProjectType::getId)
                    .collect(Collectors.toList()))
            .companyId(project.getCompany().getId())
            .projectMembers(
                project.getMembers().stream()
                    .map(
                        projectMember ->
                            ProjectMemberRequest.builder()
                                .memberId(projectMember.getMemberId())
                                .devOpsCategoryId(projectMember.getDevOpsCategoryId())
                                .build())
                    .collect(Collectors.toList()))
            .build();

    final PipelineRecommendationResponse recommendation =
        this.generatePipelineRecommendation(recommendationRequest);

    final Pipeline pipeline =
        project.getPipelines().stream().filter(Pipeline::isRecommended).findFirst().orElseThrow();

    final List<PipelineTool> pipelineToolByRecommendation =
        createPipelineToolByRecommendation(recommendation).stream()
            .peek(
                pipelineTool -> {
                  pipelineTool.setPipelineId(pipeline.getId());
                  pipelineTool.setPipeline(pipeline);
                })
            .collect(Collectors.toList());

    pipelineToolRepository.deleteAllByPipelineId(pipeline.getId());
    pipelineToolRepository.saveAll(pipelineToolByRecommendation);

    pipeline.setPipelineTools(pipelineToolByRecommendation);
    pipelineRepository.save(pipeline);
  }

  private List<PipelineTool> createPipelineToolByRecommendation(
      PipelineRecommendationResponse recommendation) {
    return recommendation.getPipelineTools().stream()
        .map(
            pipelineTool ->
                PipelineTool.builder()
                    .categoryId(pipelineTool.getCategoryId())
                    .toolId(
                        pipelineTool.getRecommendedTool() != null
                            ? pipelineTool.getRecommendedTool().getToolId()
                            : null)
                    .log(pipelineTool)
                    .build())
        .collect(Collectors.toList());
  }
}
