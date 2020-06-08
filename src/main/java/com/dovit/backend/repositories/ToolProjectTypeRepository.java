package com.dovit.backend.repositories;

import com.dovit.backend.domain.ToolProjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ramón París
 * @since 08-06-20
 */
@Repository
public interface ToolProjectTypeRepository extends JpaRepository<ToolProjectType, Long> {

  @Query(
      "select distinct toolProjectType "
          + "from ToolProjectType toolProjectType "
          + "   join toolProjectType.tool tool "
          + "   join tool.subcategories subcategory "
          + "   join subcategory.devOpsCategory category "
          + "where tool.active = true "
          + "   and category.active = true "
          + "   and subcategory.active = true "
          + "   and toolProjectType.projectTypeId in :projectTypeIds "
          + "   and category.id = :categoryId ")
  List<ToolProjectType> findRecommendationByProjectType(Long categoryId, List<Long> projectTypeIds);
}
