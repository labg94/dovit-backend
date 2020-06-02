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
      "SELECT DISTINCT tool FROM Tool tool "
          + "JOIN License license on license.tool = tool "
          + "JOIN CompanyLicense companyLicense on companyLicense.license = license "
          + "JOIN Company company on companyLicense.company = company "
          + "WHERE company.id=:companyId "
          + "ORDER BY tool.id ")
  List<Tool> findAllByCompanyId(Long companyId);

  List<Tool> findAllByActive(boolean b);

  List<Tool> findAllByActiveAndSubcategoriesContains(boolean active, DevOpsSubcategory subcategory);

  List<Tool> findAllBySubcategoriesContains(DevOpsSubcategory subcategory);
}
