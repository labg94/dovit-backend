package com.dovit.backend.repositories;

import com.dovit.backend.domain.DevOpsSubcategory;
import com.dovit.backend.domain.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
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

  @Query(
      "SELECT DISTINCT tool "
          + "from CompanyLicense cl "
          + "   join cl.license license "
          + "   join license.tool tool "
          + "   join tool.subcategories subcategory "
          + "   join subcategory.devOpsCategory category "
          + "where cl.company.id = :companyId "
          + "   and ((cl.expirationDate is null and cl.startDate is not null) "
          + "         or (:now between cl.startDate and cl.expirationDate))"
          + "   and tool.active = true "
          + "   and license.active = true "
          + "   and subcategory.active = true "
          + "   and category.active = true "
          + "   and category.id = :categoryId ")
  List<Tool> findRecommendationByCompanyLicense(Long companyId, Long categoryId, LocalDate now);

  @Query(
      "select distinct tool "
          + "from Project project "
          + "   join project.pipelines pipeline "
          + "   join pipeline.pipelineTools pipelineTool "
          + "   join pipelineTool.tool tool "
          + "where project.company.id = :companyId "
          + "   and pipeline.recommended = false "
          + "   and tool.active = true "
          + "   and pipelineTool.category.id = :categoryId ")
  List<Tool> findRecommendationByProjectHistory(Long categoryId, Long companyId);
}
