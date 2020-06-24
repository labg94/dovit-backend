package com.dovit.backend.payloads.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @author Ramón París
 * @since 23-06-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HoldingRequest {

  private Long id;

  @NotEmpty(message = "Name is a mandatory field")
  private String name;
}
