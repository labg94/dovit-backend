package com.dovit.backend.repositories;

import com.dovit.backend.domain.DevOpsSubcategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Ramón París
 * @since 14-10-2019
 */
public interface DevOpsSubCategoryRepository extends JpaRepository<DevOpsSubcategory, Long> {

  List<DevOpsSubcategory> findAllByActiveAndDevOpsCategoryIdOrderById(
      boolean active, Long categoryId);

  List<DevOpsSubcategory> findAllByDevOpsCategoryIdOrderById(Long categoryId);

  List<DevOpsSubcategory> findAllByActive(boolean b);
}
