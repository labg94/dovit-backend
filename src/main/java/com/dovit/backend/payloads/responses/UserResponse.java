package com.dovit.backend.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 03-10-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

  private Long id;
  private String name;
  private String lastName;
  private String email;
  private String rut;

  private String roleDescription;
  private Long roleId;

  private boolean active;
  private Long companyId;
  private String companyName;
}
