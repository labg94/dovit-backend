package com.dovit.backend.payloads.responses;

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
public class SuggestionResponse {

  private Long id;
  private String tool;
  private String categoryDescription;
  private Long categoryId;
  private String subCategoryDescription;
  private Long subCategoryId;
  private String message;
  private String createdAt;
  private String updatedAt;
  private boolean viewed;
  private String suggestedByFullName;
  private String suggestedByEmail;
  private String suggestedByRut;
  private Long suggestedById;
  private Long suggestedByCompanyId;
  private String suggestedByCompanyName;
}
