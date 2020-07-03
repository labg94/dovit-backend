package com.dovit.backend.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ramón París
 * @since 21-05-20
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProjectMemberResume {

  private Long projectId;
  private String projectName;
  private String projectStart;
  private String projectEndDate;
  private boolean projectFinished;
  private List<String> categoriesParticipation;
}
