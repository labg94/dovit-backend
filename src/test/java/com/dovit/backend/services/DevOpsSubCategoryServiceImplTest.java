package com.dovit.backend.services;

import com.dovit.backend.config.ModelMapperConfig;
import com.dovit.backend.domain.DevOpsSubcategory;
import com.dovit.backend.payloads.requests.SubCategoryRequest;
import com.dovit.backend.payloads.responses.SubCategoryResponse;
import com.dovit.backend.repositories.DevOpsSubCategoryRepository;
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

import static com.dovit.backend.util.DomainBuilderUtil.repositories;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * @author Ramón París
 * @since 17-05-20
 */
@ExtendWith(SpringExtension.class)
class DevOpsSubCategoryServiceImplTest {

  @InjectMocks private DevOpsSubCategoryServiceImpl devOpsSubCategoryService;
  @Mock private DevOpsSubCategoryRepository devOpsSubCategoryRepository;

  @Spy ModelMapper modelMapper = new ModelMapperConfig().modelMapper();

  @AfterEach
  void tearDown(TestInfo info) {
    if (!info.getTags().contains("SkipAfter")) modelMapper.validate();
  }

  @Test
  void findAllActivesByCategory() {
    when(devOpsSubCategoryRepository.findAllByActiveAndDevOpsCategoryIdOrderById(
            anyBoolean(), anyLong()))
        .thenReturn(List.of(repositories));
    List<SubCategoryResponse> responses = devOpsSubCategoryService.findAllActivesByCategoryId(1L);
    assertNotNull(responses);
    responses.forEach(Assert::assertNotNull);
  }

  @Test
  void findAllByCategory() {
    when(devOpsSubCategoryRepository.findAllByDevOpsCategoryIdOrderById(anyLong()))
        .thenReturn(List.of(repositories));
    List<SubCategoryResponse> responses = devOpsSubCategoryService.findAllByCategoryId(1L);
    assertNotNull(responses);
    responses.forEach(Assert::assertNotNull);
  }

  @Test
  void findAllActives() {
    when(devOpsSubCategoryRepository.findAllByActive(anyBoolean()))
        .thenReturn(List.of(repositories));
    List<SubCategoryResponse> responses = devOpsSubCategoryService.findAllActives();
    assertNotNull(responses);
    responses.forEach(Assert::assertNotNull);
  }

  @Test
  void findById() {
    when(devOpsSubCategoryRepository.findById(anyLong())).thenReturn(Optional.of(repositories));
    SubCategoryResponse response = devOpsSubCategoryService.findResponseById(1L);
    assertNotNull(response);
  }

  @Test
  @Tag("SkipAfter")
  void save() {
    when(devOpsSubCategoryRepository.save(any(DevOpsSubcategory.class)))
        .thenAnswer(i -> i.getArgument(0));
    DevOpsSubcategory response =
        devOpsSubCategoryService.save(
            SubCategoryRequest.builder().id(1L).description("asd").devOpsCategoryId(1L).build());
    assertNotNull(response);
  }

  @Test
  @Tag("SkipAfter")
  void update() {
    when(devOpsSubCategoryRepository.findById(anyLong())).thenReturn(Optional.of(repositories));
    when(devOpsSubCategoryRepository.save(any(DevOpsSubcategory.class)))
        .thenAnswer(i -> i.getArgument(0));
    DevOpsSubcategory response =
        devOpsSubCategoryService.update(
            SubCategoryRequest.builder().id(1L).description("asd").devOpsCategoryId(1L).build());
    assertNotNull(response);
  }

  @Test
  @Tag("SkipAfter")
  void toggleActive() {
    when(devOpsSubCategoryRepository.findById(anyLong())).thenReturn(Optional.of(repositories));
    when(devOpsSubCategoryRepository.save(any(DevOpsSubcategory.class)))
        .thenAnswer(i -> i.getArgument(0));

    devOpsSubCategoryService.toggleActive(1L);
  }
}
