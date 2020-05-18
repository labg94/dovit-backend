package com.dovit.backend.services;

import com.dovit.backend.domain.DevOpsCategory;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.requests.CategoryRequest;
import com.dovit.backend.model.responses.CategoryResponse;
import com.dovit.backend.repositories.DevOpsCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 17-05-20
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DevOpsCategoryServiceImpl implements DevOpsCategoryService {

  private final DevOpsCategoryRepository devOpsCategoryRepository;
  private final ModelMapper modelMapper;

  @Override
  public List<CategoryResponse> findAllActives() {
    List<DevOpsCategory> devOpsCategories = devOpsCategoryRepository.findAllByActiveOrderById(true);
    return devOpsCategories.stream()
        .map(c -> modelMapper.map(c, CategoryResponse.class))
        .collect(Collectors.toList());
  }

  @Override
  public List<CategoryResponse> findAll() {
    List<DevOpsCategory> devOpsCategories = devOpsCategoryRepository.findAll();
    return devOpsCategories.stream()
        .map(c -> modelMapper.map(c, CategoryResponse.class))
        .collect(Collectors.toList());
  }

  @Override
  public CategoryResponse findById(Long id) {
    DevOpsCategory devOpsCategory = findDomainById(id);
    return modelMapper.map(devOpsCategory, CategoryResponse.class);
  }

  @Override
  public DevOpsCategory save(CategoryRequest devOpsCategoryRequest) {
    devOpsCategoryRequest.setId(null);
    DevOpsCategory devOpsCategory = modelMapper.map(devOpsCategoryRequest, DevOpsCategory.class);
    devOpsCategory.setActive(true);
    return devOpsCategoryRepository.save(devOpsCategory);
  }

  @Override
  public DevOpsCategory update(CategoryRequest devOpsCategoryRequest) {
    DevOpsCategory devOpsCategory = this.findDomainById(devOpsCategoryRequest.getId());
    devOpsCategory.setDescription(devOpsCategoryRequest.getDescription());
    return devOpsCategoryRepository.save(devOpsCategory);
  }

  @Override
  public void toggleActive(Long id) {
    DevOpsCategory devOpsCategory = this.findDomainById(id);
    devOpsCategory.setActive(!devOpsCategory.isActive());
    devOpsCategoryRepository.save(devOpsCategory);
  }

  private DevOpsCategory findDomainById(Long id) {
    return devOpsCategoryRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("DevOps Category", "id", id));
  }
}
