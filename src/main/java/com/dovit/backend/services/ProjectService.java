package com.dovit.backend.services;

import com.dovit.backend.domain.Project;
import com.dovit.backend.payloads.requests.ProjectRequest;
import com.dovit.backend.payloads.responses.ProjectMemberRecommendation;
import com.dovit.backend.payloads.responses.ProjectResponse;

import java.util.List;

/**
 * @author Ramón París
 * @since 09-12-2019
 */
public interface ProjectService {

  Project saveProject(ProjectRequest request);

  List<ProjectResponse> findAllByCompanyId(Long companyId);

  ProjectResponse findByProjectId(Long projectId);

  Project updateProject(ProjectRequest request);

  List<ProjectMemberRecommendation> findMemberRecommendation(Long companyId);
}
