package com.dovit.backend.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 17-05-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {

  private Long id;

  private String description;
  private boolean active;
  private String createdAt;
  private String updatedAt;
}
