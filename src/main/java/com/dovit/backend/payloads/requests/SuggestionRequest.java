package com.dovit.backend.payloads.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @author Ramón París
 * @since 24-05-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuggestionRequest {

  @NotEmpty(message = "Tool is a mandatory field")
  private String tool;

  private Long categoryId;
  private Long subCategoryId;
  private String message;
}
