package com.dovit.backend.repositories;

import com.dovit.backend.payloads.responses.MemberResponseResume;
import com.dovit.backend.payloads.responses.charts.ChartTopToolsByProject;

import java.util.List;

/**
 * @author Ramón París
 * @since 14-10-2019
 */
public interface CustomRepository {

  List<MemberResponseResume> findAllMembersResumeByCompanyId(Long companyId, boolean limit);

  List<ChartTopToolsByProject> findTopToolsByProject(Long companyId);
}
