package com.dovit.backend.payloads.responses;

import com.dovit.backend.model.ToolRecommendationDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ramón París
 * @since 15-06-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryToolResponse {

  private Long categoryId;
  private String categoryDescription;
  private List<ToolRecommendationDTO> tools;
}
