package com.dovit.backend.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ramón París
 * @since 08-12-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponseDetail {

  private Long id;
  private Long companyId;
  private String companyName;
  private String memberName;
  private String memberLastName;
  private Boolean active;
  private Long availableStatusId;
  private String availableStatusDescription;
  private Long activeProjectsQty;
  private Long allProjectsQty;
  private Long closedProjectsQty;
  private List<ProfileResponse> profiles;
  private List<ToolProfileResponse> tools;
  private List<ProjectMemberResume> projects;
}
