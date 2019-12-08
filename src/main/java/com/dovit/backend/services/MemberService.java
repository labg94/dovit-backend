package com.dovit.backend.services;

import com.dovit.backend.domain.Member;
import com.dovit.backend.model.requests.MemberRequest;

public interface MemberService {


    Member agregar(MemberRequest member);
}
