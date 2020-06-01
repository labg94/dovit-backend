package com.dovit.backend.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @author Ramón París
 * @since 31-05-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectTypeRequest {

  private Long id;

  @NotEmpty(message = "Description is a mandatory field")
  private String description;

  private String observation;
}
