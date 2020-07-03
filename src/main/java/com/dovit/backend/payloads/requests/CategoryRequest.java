package com.dovit.backend.payloads.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author Ramón París
 * @since 17-05-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {

  private Long id;

  @NotBlank(message = "Description is a mandatory field")
  private String description;
}
