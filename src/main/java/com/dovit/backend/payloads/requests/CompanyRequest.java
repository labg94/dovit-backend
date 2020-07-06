package com.dovit.backend.payloads.requests;

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

  @NotEmpty(message = "Company name cannot be empty")
  private String name;

  private boolean active;

  private Long holdingId;
}
