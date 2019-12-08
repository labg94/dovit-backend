package com.dovit.backend.repositories;

import com.dovit.backend.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {



}
