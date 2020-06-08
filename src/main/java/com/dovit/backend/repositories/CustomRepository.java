package com.dovit.backend.repositories;

import com.dovit.backend.model.ToolRecommendationDTO;
import com.dovit.backend.payloads.responses.MemberResponseResume;

import java.util.List;

/**
 * @author Ramón París
 * @since 14-10-2019
 */
public interface CustomRepository {

  List<MemberResponseResume> findAllMembersResumeByCompanyId(Long companyId);

  List<ToolRecommendationDTO> findRecommendationByCompanyLicense(Long companyId, Long categoryId);

  List<ToolRecommendationDTO> findRecommendationByProjectType(
      Long categoryId, String projectTypeIds);

  List<ToolRecommendationDTO> findRecommendationByProjectHistory(Long categoryId, Long companyId);

  List<ToolRecommendationDTO> findRecommendationByMembers(Long categoryId, String membersId);
}
