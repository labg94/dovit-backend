package com.dovit.backend.repositories;

import com.dovit.backend.domain.ToolProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolProfileRepository extends JpaRepository<ToolProfile,Long> {

  ToolProfile findAllByMemberId(Long memberId);
}
