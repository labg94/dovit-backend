package com.dovit.backend.repositories;

import com.dovit.backend.domain.DevOpsSubcategory;
import com.dovit.backend.domain.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Ramón París
 * @since 14-10-2019
 */
public interface ToolRepository extends JpaRepository<Tool, Long> {

  @Query(
      "SELECT DISTINCT t FROM Tool t "
          + "JOIN t.licenses l "
          + "JOIN l.companyLicenses cl "
          + "JOIN cl.company c "
          + "WHERE c.id=:companyId "
          + "ORDER BY t ")
  List<Tool> findAllByCompanyId(Long companyId);

  List<Tool> findAllByActive(boolean b);

  List<Tool> findAllByActiveAndSubcategoriesContains(boolean active, DevOpsSubcategory subcategory);

  List<Tool> findAllBySubcategoriesContains(DevOpsSubcategory subcategory);
}
