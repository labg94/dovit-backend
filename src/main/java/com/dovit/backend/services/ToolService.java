package com.dovit.backend.services;

import com.dovit.backend.domain.Tool;
import com.dovit.backend.model.ToolRecommendationDTO;
import com.dovit.backend.payloads.requests.ProjectMemberRequest;
import com.dovit.backend.payloads.requests.ToolRequest;
import com.dovit.backend.payloads.responses.ToolResponse;

import java.util.List;

/**
 * @author Ramón París
 * @since 01-10-2019
 */
public interface ToolService {

  List<ToolResponse> findAllToolsOfCompany(Long companyId);

  List<ToolResponse> findAllTools();

  ToolResponse findById(Long toolId);

  Tool save(ToolRequest request);

  Tool update(ToolRequest request);

  void toggleActive(Long toolId);

  List<ToolResponse> findAllActiveTools();

  List<ToolResponse> findAllActiveBySubcategory(Long subcategoryId);

  List<ToolResponse> findAllBySubcategory(Long subcategoryId);

  List<ToolRecommendationDTO> findRecommendationByMembers(
      Long categoryId, Long companyId, List<ProjectMemberRequest> projectMembers);

  List<ToolRecommendationDTO> findRecommendationByLicense(Long categoryId, Long companyId);

  List<ToolRecommendationDTO> findRecommendationByProjectType(
      Long categoryId, Long companyId, List<Long> projectTypeIds);

  List<ToolRecommendationDTO> findRecommendationByProjectHistory(Long categoryId, Long companyId);
}
