package com.dovit.backend.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 08-06-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolProjectTypeRequest {

  private Long toolId;
  private Long projectTypeId;
}
