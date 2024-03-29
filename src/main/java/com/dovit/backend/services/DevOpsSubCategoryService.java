package com.dovit.backend.services;

import com.dovit.backend.domain.DevOpsSubcategory;
import com.dovit.backend.payloads.requests.SubCategoryRequest;
import com.dovit.backend.payloads.responses.SubCategoryResponse;

import java.util.List;

/**
 * @author Ramón París
 * @since 17-05-20
 */
public interface DevOpsSubCategoryService {

  List<SubCategoryResponse> findAllActivesByCategoryId(Long categoryId);

  SubCategoryResponse findResponseById(Long id);

  DevOpsSubcategory save(SubCategoryRequest devOpsCategoryRequest);

  DevOpsSubcategory update(SubCategoryRequest request);

  void toggleActive(Long id);

  List<SubCategoryResponse> findAllByCategoryId(Long categoryId);

  List<SubCategoryResponse> findAllActives();

  DevOpsSubcategory findById(Long id);
}
