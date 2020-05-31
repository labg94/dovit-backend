package com.dovit.backend.repositories;

import com.dovit.backend.domain.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ramón París
 * @since 31-05-20
 */
@Repository
public interface ProjectTypeRepository extends JpaRepository<ProjectType, Long> {}
