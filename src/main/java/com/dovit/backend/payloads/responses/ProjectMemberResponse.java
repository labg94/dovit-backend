package com.dovit.backend.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 15-06-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectMemberResponse {

  private Long memberId;
  private String memberName;
  private String memberLastName;
  private String memberProfiles;
}
