package com.dovit.backend.services;

import com.dovit.backend.domain.Member;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.requests.MemberRequest;
import com.dovit.backend.model.responses.MemberResponse;

import java.util.List;

public interface MemberService {


    Member save(MemberRequest member);

    MemberResponse findById(Long memberId) throws ResourceNotFoundException;

    List<MemberResponse> findAllByCompanyId(Long companyId) throws ResourceNotFoundException;
}
