package com.dovit.backend.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * Authentication request for sign in
 *
 * @author Ramón París
 * @since 02-10-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRequest {

  @NotEmpty(message = "Email cannot be empty")
  @Email(message = "Email with wrong format")
  private String email;

  @NotEmpty(message = "Password cannot be empty")
  private String password;
}
