package com.dovit.backend.services;

import com.dovit.backend.payloads.responses.MemberResponseResume;
import com.dovit.backend.payloads.responses.charts.*;

import java.util.List;

/**
 * @author Ramón París
 * @since 28-06-20
 */
public interface ChartService {

  List<ChartTopSeniorMemberResponse> findTopSeniorMembers(Long companyId);

  List<MemberResponseResume> findTopWorkers(Long companyId);

  List<ChartTopToolsByMembersResponse> findTopMemberTools(Long companyId);

  List<ChartMemberByCategory> findQtyMemberByCategory(Long companyId);

  List<ChartTopToolsByProject> findTopToolsByProject(Long companyId);

  List<ChartTopProjectTypes> findTopProjectTypes(Long companyId);
}
