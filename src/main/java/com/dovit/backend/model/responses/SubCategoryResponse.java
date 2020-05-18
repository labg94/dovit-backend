package com.dovit.backend.model.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 17-05-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryResponse {

  private Long id;
  private String description;
  private boolean active;
  private String createdAt;
  private String updatedAt;
  private Long devOpsCategoryId;
  private String devOpsCategoryDescription;
}
