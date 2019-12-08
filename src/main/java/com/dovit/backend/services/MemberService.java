package com.dovit.backend.services;

import com.dovit.backend.domain.Member;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.requests.MemberRequest;

public interface MemberService {


    Member agregar(MemberRequest member);

    Member findById(Long memberId, Long companyId) throws ResourceNotFoundException;
}
