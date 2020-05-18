package com.dovit.backend.services;

import com.dovit.backend.config.ModelMapperConfig;
import com.dovit.backend.domain.DevOpsCategory;
import com.dovit.backend.model.requests.CategoryRequest;
import com.dovit.backend.model.responses.CategoryResponse;
import com.dovit.backend.repositories.DevOpsCategoryRepository;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.dovit.backend.util.DomainBuilderUtil.devOpsCategoryPlanning;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * @author Ramón París
 * @since 17-05-20
 */
@ExtendWith(SpringExtension.class)
class DevOpsCategoryServiceImplTest {

  @InjectMocks private DevOpsCategoryServiceImpl devOpsCategoryService;
  @Mock DevOpsCategoryRepository devOpsCategoryRepository;
  @Spy ModelMapper modelMapper = new ModelMapperConfig().modelMapper();

  @AfterEach
  void tearDown(TestInfo info) {
    if (!info.getTags().contains("SkipAfter")) modelMapper.validate();
  }

  @Test
  void findAllActives() {
    when(devOpsCategoryRepository.findAllByActiveOrderById(anyBoolean()))
        .thenReturn(List.of(devOpsCategoryPlanning));
    List<CategoryResponse> responses = devOpsCategoryService.findAllActives();
    assertNotNull(responses);
    responses.forEach(Assert::assertNotNull);
  }

  @Test
  void findAll() {
    when(devOpsCategoryRepository.findAll()).thenReturn(List.of(devOpsCategoryPlanning));
    List<CategoryResponse> responses = devOpsCategoryService.findAll();
    assertNotNull(responses);
    responses.forEach(Assert::assertNotNull);
  }

  @Test
  void findById() {
    when(devOpsCategoryRepository.findById(anyLong()))
        .thenReturn(Optional.of(devOpsCategoryPlanning));
    CategoryResponse devOpsCategory = devOpsCategoryService.findById(1L);
    assertNotNull(devOpsCategory);
  }

  @Test
  @Tag("SkipAfter")
  void save() {
    when(devOpsCategoryRepository.save(any(DevOpsCategory.class)))
        .thenAnswer(i -> i.getArgument(0));
    DevOpsCategory devOpsCategory =
        devOpsCategoryService.save(CategoryRequest.builder().id(1L).description("asd").build());
    assertNotNull(devOpsCategory);
  }

  @Test
  @Tag("SkipAfter")
  void update() {
    when(devOpsCategoryRepository.findById(anyLong()))
        .thenReturn(Optional.of(devOpsCategoryPlanning));
    when(devOpsCategoryRepository.save(any(DevOpsCategory.class)))
        .thenAnswer(i -> i.getArgument(0));
    DevOpsCategory devOpsCategory =
        devOpsCategoryService.update(CategoryRequest.builder().id(1L).description("asd").build());
    assertNotNull(devOpsCategory);
  }

  @Test
  @Tag("SkipAfter")
  void toggleActive() {
    when(devOpsCategoryRepository.findById(anyLong()))
        .thenReturn(Optional.of(devOpsCategoryPlanning));
    when(devOpsCategoryRepository.save(any(DevOpsCategory.class)))
        .thenAnswer(i -> i.getArgument(0));

    devOpsCategoryService.toggleActive(1L);
  }
}
