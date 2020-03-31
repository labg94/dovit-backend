package com.dovit.backend.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @author Ramón París
 * @since 03-10-2019
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CompanyRequest {

  private Long id;

  @NotEmpty(message = "Nombre de empresa no puede estar vacío")
  private String name;
}
