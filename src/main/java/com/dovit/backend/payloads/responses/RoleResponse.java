package com.dovit.backend.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 01-07-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {
  private Long id;
  private String description;
}
