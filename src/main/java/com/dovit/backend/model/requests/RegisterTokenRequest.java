package com.dovit.backend.model.requests;

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

  @Email
  @NotEmpty
  private String email;

  @NotNull
  private Long companyId;
}
