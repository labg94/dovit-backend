package com.dovit.backend.services;

import com.dovit.backend.domain.DevOpsCategory;
import com.dovit.backend.domain.DevOpsSubcategory;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.RecommendationPointsDTO;
import com.dovit.backend.model.ToolRecommendationDTO;
import com.dovit.backend.payloads.requests.PipelineRecommendationRequest;
import com.dovit.backend.payloads.responses.CategoryRecommendationResponse;
import com.dovit.backend.payloads.responses.PipelineResponse;
import com.dovit.backend.repositories.DevOpsCategoryRepository;
import com.dovit.backend.repositories.PipelineRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
  private final DevOpsCategoryRepository devOpsCategoryRepository;
  private final ToolService toolService;
  private final ModelMapper modelMapper;

  @Override
  @Transactional
  public PipelineResponse generatePipelineRecommendation(PipelineRecommendationRequest request) {

    final List<DevOpsCategory> categories = devOpsCategoryRepository.findAllByActiveOrderById(true);

    final List<CategoryRecommendationResponse> categoriesRecommendation =
        categories.stream()
            .map(
                category -> {
                  List<ToolRecommendationDTO> allTools =
                      category.getSubcategories().stream()
                          .map(DevOpsSubcategory::getTools)
                          .flatMap(Collection::stream)
                          .distinct()
                          .map(
                              tool ->
                                  modelMapper
                                      .map(tool, ToolRecommendationDTO.class)
                                      .toBuilder()
                                      .points(Collections.emptyList())
                                      .build())
                          .collect(Collectors.toList());

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
                              allTools,
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
                                return currentTool
                                    .toBuilder()
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
                  // TODO acá se debe hacer la lógica de los precios de las licencias y presupuesto
                  // para elegir una en caso de que sea más de una con el mismo puntaje
                  .orElse(null);

          category.setRecommendedTool(recommendedTool);
          category.setOtherTools(
              category.getAllTools().stream()
                  .filter(Objects::nonNull)
                  .filter(
                      r ->
                          recommendedTool != null
                              && !r.getToolId().equals(recommendedTool.getToolId()))
                  .sorted(Comparator.comparing(ToolRecommendationDTO::getTotalPoints).reversed())
                  .collect(Collectors.toList()));
        });

    // TODO Integrar exclude tools id para presupuesto
    //    final Double totalCost =
    //        categoriesRecommendation.stream()
    //            .collect(Collectors.groupingBy(c -> c.getRecommendedTool().getToolId()))
    //            .values()
    //            .stream()
    //            .map(
    //                values ->
    //                    values.stream()
    //                        .max(Comparator.comparing(c ->
    // c.getRecommendedTool().getTotalPrice()))
    //                        .map(c -> c.getRecommendedTool().getTotalPrice())
    //                        .orElse(0.0))
    //            .reduce(0.0, Double::sum);

    return PipelineResponse.builder()
        .recommended(true)
        .pipelineTools(categoriesRecommendation)
        .cost(null)
        .build();
  }

  @Override
  public List<PipelineResponse> findAllByProjectId(Long projectId) {
    return null;
  }
}
