package com.dovit.backend.payloads.requests;

import com.dovit.backend.annotations.Rut;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * This class is for the sign up of the client only
 *
 * @author Ramón París
 * @since 02-10-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequest {

  @NotEmpty(message = "Token cannot be empty")
  private String registrationToken;

  @NotBlank(message = "Name cannot be empty")
  private String name;

  @NotBlank(message = "Last name cannot be empty")
  private String lastName;

  @NotBlank(message = "Rut cannot be empty")
  @Pattern(
      message = "RUT's format is wrong. Please write it without dots (.) and with dashes (-)",
      regexp = "^[0-9]+[-|‐][0-9kK]$")
  @Rut
  private String rut;

  @NotBlank(message = "Password cannot be empty")
  @Size(message = "Password length must be between 6 and 20 characters.", min = 6, max = 20)
  private String password;
}
