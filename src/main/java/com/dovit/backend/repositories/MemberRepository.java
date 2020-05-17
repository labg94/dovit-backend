package com.dovit.backend.repositories;

import com.dovit.backend.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByIdAndCompanyId(long id, Long company_id);

    List<Member> findAllByCompanyId(Long companyId);
}
