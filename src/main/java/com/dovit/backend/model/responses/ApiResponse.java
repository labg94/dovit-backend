package com.dovit.backend.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Payload to give responses in Create, Update and Delete operations
 *
 * @author Ramón París
 * @since 02-10-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {

  private Boolean success;
  private String message;
  private Long id;

  public ApiResponse(Boolean success, String message) {
    this.success = success;
    this.message = message;
  }
}
