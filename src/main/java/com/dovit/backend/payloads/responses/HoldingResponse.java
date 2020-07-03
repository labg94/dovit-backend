package com.dovit.backend.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ramón París
 * @since 23-06-20
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HoldingResponse {

  private Long id;
  private String name;
  private List<CompanyResponse> companies;
}
