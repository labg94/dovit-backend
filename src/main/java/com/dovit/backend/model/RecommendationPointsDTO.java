package com.dovit.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 06-06-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendationPointsDTO {

  private String category;
  private int points;
}
