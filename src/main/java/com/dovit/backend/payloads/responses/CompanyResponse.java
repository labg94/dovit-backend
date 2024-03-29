package com.dovit.backend.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 03-10-2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponse {

  private Long id;
  private String name;
  private boolean active;
}
