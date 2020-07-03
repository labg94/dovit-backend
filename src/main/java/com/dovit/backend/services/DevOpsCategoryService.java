package com.dovit.backend.services;

import com.dovit.backend.domain.DevOpsCategory;
import com.dovit.backend.payloads.requests.CategoryRequest;
import com.dovit.backend.payloads.responses.CategoryResponse;

import java.util.List;

/**
 * @author Ramón París
 * @since 17-05-20
 */
public interface DevOpsCategoryService {

  List<CategoryResponse> findAllActives();

  CategoryResponse findById(Long id);

  DevOpsCategory save(CategoryRequest devOpsCategoryRequest);

  DevOpsCategory update(CategoryRequest devOpsCategoryRequest);

  void toggleActive(Long id);

  List<CategoryResponse> findAll();
}
