package com.dovit.backend.util;

import com.dovit.backend.model.RecommendationPointsDTO;
import com.dovit.backend.model.ToolRecommendationDTO;

/**
 * @author Ramón París
 * @since 06-06-20
 */
public class RecommendationBuilderUtil {

  public static final ToolRecommendationDTO gitlab =
      ToolRecommendationDTO.builder()
          .toolId(1L)
          .toolName("Gitlab")
          .toolDescription("Gitlab on premise")
          .usersQty(4)
          .totalPrice(500.0)
          .build();

  public static final ToolRecommendationDTO azureDevOps =
      ToolRecommendationDTO.builder()
          .toolId(2L)
          .toolName("Azure DevOps")
          .toolDescription("Azure DevOps on premise")
          .usersQty(3)
          .totalPrice(20.0)
          .build();

  public static final ToolRecommendationDTO jira =
      ToolRecommendationDTO.builder()
          .toolId(3L)
          .toolName("Jira")
          .toolDescription("Jira on premise")
          .usersQty(3)
          .totalPrice(20.0)
          .build();

  public static final ToolRecommendationDTO jenkins =
      ToolRecommendationDTO.builder()
          .toolId(4L)
          .toolName("Jenkins")
          .toolDescription("Jenkins on premise")
          .build();

  public static final ToolRecommendationDTO circleCI =
      ToolRecommendationDTO.builder()
          .toolId(5L)
          .toolName("Circle CI")
          .toolDescription("CircleCI on premise")
          .build();

  public static final ToolRecommendationDTO bamboo =
      ToolRecommendationDTO.builder()
          .toolId(6L)
          .toolName("Atlassian Bamboo")
          .toolDescription("Bamboo on premise")
          .build();

  public static final RecommendationPointsDTO licensePoints =
      RecommendationPointsDTO.builder().category("License").points(5).build();

  public static final RecommendationPointsDTO memberPoints =
      RecommendationPointsDTO.builder().category("Member").points(3).build();

  public static final RecommendationPointsDTO projectTypePoints =
      RecommendationPointsDTO.builder().category("Project Type").points(4).build();

  public static final RecommendationPointsDTO historyPoints =
      RecommendationPointsDTO.builder().category("History").points(2).build();
}
