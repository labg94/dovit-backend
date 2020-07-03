package com.dovit.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 19-06-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuggestionEmailDTO {

  private String issuerName;
  private String companyName;
  private String toolName;
  private String category;
  private String subCategory;
  private String message;
  private String issuerEmail;
}
