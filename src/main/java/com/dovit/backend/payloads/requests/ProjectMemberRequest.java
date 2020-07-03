package com.dovit.backend.payloads.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author Ramón París
 * @since 09-12-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectMemberRequest {

  @NotNull(message = "Member cannot be null")
  private Long memberId;

  @NotNull(message = "Category cannot be null")
  private Long devOpsCategoryId;
}
