package com.dovit.backend.model.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author Ramón París
 * @since 09-12-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {

  private Long id;
  private String name;
  private Date start;
  private String observation;
  private Long companyId;
  private String companyName;
  private Boolean finished;
  private List<ProjectMemberResponse> members;
}
