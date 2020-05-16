package com.dovit.backend.repositories;

import com.dovit.backend.domain.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ramón París
 * @since 09-12-2019
 */
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {

  void deleteAllByProjectId(Long projectId);
}
