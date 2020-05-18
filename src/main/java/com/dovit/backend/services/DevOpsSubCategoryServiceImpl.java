package com.dovit.backend.services;

import com.dovit.backend.domain.DevOpsCategory;
import com.dovit.backend.domain.DevOpsSubcategory;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.requests.SubCategoryRequest;
import com.dovit.backend.model.responses.SubCategoryResponse;
import com.dovit.backend.repositories.DevOpsSubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 17-05-20
 */
@Service
@RequiredArgsConstructor
public class DevOpsSubCategoryServiceImpl implements DevOpsSubCategoryService {

  private final DevOpsSubCategoryRepository devOpsSubcategoryRepository;
  private final ModelMapper modelMapper;

  @Override
  public List<SubCategoryResponse> findAllActives(Long categoryId) {
    List<DevOpsSubcategory> devOpsSubcategories =
        devOpsSubcategoryRepository.findAllByActiveAndDevOpsCategoryIdOrderById(true, categoryId);
    return devOpsSubcategories.stream()
        .map(c -> modelMapper.map(c, SubCategoryResponse.class))
        .collect(Collectors.toList());
  }

  @Override
  public SubCategoryResponse findById(Long id) {
    DevOpsSubcategory devOpsSubcategory = findDomainById(id);
    return modelMapper.map(devOpsSubcategory, SubCategoryResponse.class);
  }

  @Override
  @Transactional
  public DevOpsSubcategory save(SubCategoryRequest request) {
    request.setId(null);
    DevOpsSubcategory devOpsSubcategory = modelMapper.map(request, DevOpsSubcategory.class);
    devOpsSubcategory.setActive(true);
    return devOpsSubcategoryRepository.save(devOpsSubcategory);
  }

  @Override
  @Transactional
  public DevOpsSubcategory update(SubCategoryRequest request) {
    DevOpsSubcategory devOpsSubcategory = this.findDomainById(request.getId());
    devOpsSubcategory.setDescription(request.getDescription());
    devOpsSubcategory.setDevOpsCategory(
        DevOpsCategory.builder().id(request.getDevOpsCategoryId()).build());
    return devOpsSubcategoryRepository.save(devOpsSubcategory);
  }

  @Override
  public void toggleActive(Long id) {
    DevOpsSubcategory devOpsSubcategory = this.findDomainById(id);
    devOpsSubcategory.setActive(!devOpsSubcategory.isActive());
    devOpsSubcategoryRepository.save(devOpsSubcategory);
  }

  @Override
  public List<SubCategoryResponse> findAll(Long categoryId) {
    List<DevOpsSubcategory> devOpsSubcategories =
        devOpsSubcategoryRepository.findAllByDevOpsCategoryIdOrderById(categoryId);
    return devOpsSubcategories.stream()
        .map(c -> modelMapper.map(c, SubCategoryResponse.class))
        .collect(Collectors.toList());
  }

  private DevOpsSubcategory findDomainById(Long id) {
    return devOpsSubcategoryRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("DevOps subcategory", "id", id));
  }
}
