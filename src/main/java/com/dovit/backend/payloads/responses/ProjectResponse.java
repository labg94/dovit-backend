package com.dovit.backend.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
  private String start;
  private String endDate;
  private String observation;
  private Long companyId;
  private String companyName;
  private Boolean finished;
  private List<ProjectMemberCategoryResponse> categoryMembers;
  private List<ProjectTypeResponse> projectTypes;
  private SelectedPipelineResponse selectedPipeline;
  private PipelineRecommendationResponse recommendedPipeline;
}
