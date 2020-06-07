package com.dovit.backend.payloads.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ramón París
 * @since 31-05-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PipelineRecommendationRequest {

  private List<ProjectMemberRequest> projectMembers;
  private Double budget;
  private List<Long> projectTypeIds;
  private List<Long> categoriesToExcludeIds;
  private Long companyId;
}
