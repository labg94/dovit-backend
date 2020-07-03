package com.dovit.backend.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 15-06-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResumeResponse {
  private Long id;
  private String name;
  private String start;
  private String endDate;
  private String observation;
  private Boolean finished;
}
