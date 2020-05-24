package com.dovit.backend.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 24-05-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuggestionRequest {

  private String tool;
  private String category;
  private String subcategory;
  private String message;
}
