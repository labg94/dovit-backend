package com.dovit.backend.payloads.responses;

import com.dovit.backend.model.ToolRecommendationDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ramón París
 * @since 31-05-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CategoryRecommendationResponse {

  private Long categoryId;
  private String categoryDescription;
  private ToolRecommendationDTO recommendedTool;
  private List<ToolRecommendationDTO> otherTools;
  @JsonIgnore private List<ToolRecommendationDTO> allTools;
  private int usersQty;
}
