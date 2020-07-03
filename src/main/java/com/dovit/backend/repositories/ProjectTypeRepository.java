package com.dovit.backend.repositories;

import com.dovit.backend.domain.ProjectType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Ramón París
 * @since 31-05-20
 */
@Repository
public interface ProjectTypeRepository extends JpaRepository<ProjectType, Long> {

  @Query(
      "select projectTypes, count(project) from ProjectType projectTypes "
          + "left join projectTypes.project project "
          + "where project.company.id = :companyId or project.company.id is null  "
          + "group by projectTypes "
          + "order by count(project) desc")
  Page<Object[]> findTopProjectTypes(Pageable pageable, Long companyId);
}
