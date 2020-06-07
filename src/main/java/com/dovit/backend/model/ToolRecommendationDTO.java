package com.dovit.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ramón París
 * @since 06-06-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ToolRecommendationDTO {

  private Long toolId;
  private String toolName;
  private String toolDescription;
  private List<RecommendationPointsDTO> points;
  private int totalPoints;
  private double totalPrice;
  private int usersQty;
}
