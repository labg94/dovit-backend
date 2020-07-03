package com.dovit.backend.payloads.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Payload to request the token to create users. This token includes relevant attributes like email
 * of the client and company.
 *
 * @author Ramón París
 * @since 02-10-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterTokenRequest {

  @Email(message = "Email does not have a valid format")
  @NotEmpty(message = "Email cannot be null")
  private String email;

  @NotNull(message = "companyId cannot be null")
  private Long companyId;

  @NotNull(message = "Role cannot be null")
  private Long roleId;
}
