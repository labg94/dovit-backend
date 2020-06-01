package com.dovit.backend.model.requests;

import lombok.AllArgsConstructor;
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
public class PipelineRecommendationRequest {

  private List<ProjectMemberRequest> projectMemberRequests;
  private Double budget;
  private List<Long> projectTypeIds;
  private List<Long> categoriesToExcludeIds;
  private Long companyId;
}
