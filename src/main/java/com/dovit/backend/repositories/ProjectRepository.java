package com.dovit.backend.repositories;

import com.dovit.backend.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Ramón París
 * @since 09-12-2019
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {

  List<Project> findAllByCompanyId(Long companyId);
}
