package com.dovit.backend.repositories;

import com.dovit.backend.domain.ToolProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ToolProfileRepository extends JpaRepository<ToolProfile, Long> {

  ToolProfile findAllByMemberId(Long memberId);

  void deleteAllByMemberId(Long memberId);

  @Query(
      "select distinct toolProfile "
          + "from Tool tool "
          + "   join tool.subcategories subcategory "
          + "   join subcategory.devOpsCategory category "
          + "   join tool.toolProfile toolProfile "
          + "   join toolProfile.level level "
          + "where toolProfile.memberId in :memberIds "
          + "   and category.id = :categoryId "
          + "   and category.active = true "
          + "   and subcategory.active = true")
  List<ToolProfile> findRecommendationByMembers(Long categoryId, List<Long> memberIds);
}
