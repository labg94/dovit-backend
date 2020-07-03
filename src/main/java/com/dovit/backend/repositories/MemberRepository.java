package com.dovit.backend.repositories;

import com.dovit.backend.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

  List<Member> findAllByCompanyId(Long companyId);

  List<Member> findAllByCompanyIdAndIdIn(Long companyId, List<Long> memberIds);

  @Query(
      value =
          "SELECT DISTINCT m, sum(level.points) FROM Member m "
              + "join m.toolProfile toolProfile "
              + "join toolProfile.level level "
              + "join toolProfile.tool tool "
              + "join tool.subcategories subCategories "
              + "join subCategories.devOpsCategory devOpsCategory "
              + "where m.company.id = :companyId "
              + "group by m "
              + "order by sum(level.points) desc")
  Page<Object[]> findTopSeniorMembers(Long companyId, Pageable pageable);
}
