package com.dovit.backend.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 09-12-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectMemberResponse {

  private Long memberId;
  private String memberName;
  private String memberLastName;
  private Long categoryId;
  private String categoryDescription;
}
