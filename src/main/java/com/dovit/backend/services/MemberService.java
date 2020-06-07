package com.dovit.backend.services;

import com.dovit.backend.domain.Member;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.payloads.requests.MemberRequest;
import com.dovit.backend.payloads.responses.MemberResponseDetail;
import com.dovit.backend.payloads.responses.MemberResponseResume;

import java.util.List;

public interface MemberService {

  Member save(MemberRequest member);

  MemberResponseDetail findById(Long memberId) throws ResourceNotFoundException;

  List<MemberResponseResume> findAllByCompanyId(Long companyId);

  Member update(MemberRequest request);

  void areMembersInCompany(List<Long> membersId, Long companyId);
}
