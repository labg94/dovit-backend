package com.dovit.backend.payloads.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Ramón París
 * @since 17-05-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubCategoryRequest {

  private Long id;

  @NotBlank(message = "Description is a mandatory fill")
  private String description;

  @NotNull(message = "Please select a category")
  private Long devOpsCategoryId;
}
