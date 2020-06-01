package com.dovit.backend.model.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 31-05-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTypeResponse {

  private Long projectTypeId;
  private String projectTypeDescription;
  private String projectTypeObservation;
}
