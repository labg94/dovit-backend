package com.dovit.backend.payloads.requests;

import com.dovit.backend.annotations.Rut;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * To register new users from the administrator site. It can create / update any type of users
 *
 * @author Ramón París
 * @since 02-10-2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

  @Nullable private Long id;

  @NotBlank(message = "Name cannot be null")
  private String name;

  @NotBlank(message = "Last name cannot be null")
  private String lastName;

  @NotBlank(message = "Email cannot be null")
  @Email(message = "Email does not have a valid format")
  private String email;

  @NotBlank(message = "Rut is a mandatory field")
  @Pattern(
      message = "RUT's format is wrong. Please write it without dots (.) and with dashes (-)",
      regexp = "^[0-9]+[-|‐][0-9kK]$")
  @Rut
  private String rut;

  private String password;

  @NotNull private Boolean active;

  @NotNull(message = "Remember to select at least one role")
  private Long roleId;

  private Long companyId;
}
