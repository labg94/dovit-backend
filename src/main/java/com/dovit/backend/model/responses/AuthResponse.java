package com.dovit.backend.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Payload to response the Sign In request
 *
 * @author Ramón París
 * @since 02-10-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

  private String accessToken;
  private String tokenType = "Bearer";
  private String name;
  private String lastName;
  private String role;
  private String company;
  private Long companyId;
  private Long userId;

  public AuthResponse(
      String accessToken, String name, String lastName, String role, String company, Long userId) {
    this.accessToken = accessToken;
    this.name = name;
    this.lastName = lastName;
    this.role = role;
    this.company = company;
    this.userId = userId;
  }
}
