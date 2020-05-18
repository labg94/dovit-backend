package com.dovit.backend.controllers;

import com.dovit.backend.domain.DevOpsSubcategory;
import com.dovit.backend.model.requests.SubCategoryRequest;
import com.dovit.backend.model.responses.SubCategoryResponse;
import com.dovit.backend.services.DevOpsSubCategoryServiceImpl;
import com.dovit.backend.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * @author Ramón París
 * @since 17-05-20
 */
@ExtendWith(SpringExtension.class)
class DevOpsSubCategoryControllerTest {

  @InjectMocks private DevOpsSubCategoryController controller;
  @Mock private DevOpsSubCategoryServiceImpl subCategoryService;
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  void findAllActives() {
    when(subCategoryService.findAllActives(anyLong())).thenReturn(new ArrayList<>());
    TestUtils.testGetRequest(mockMvc, "/category/1/subcategories/actives");
  }

  @Test
  void findAll() {
    when(subCategoryService.findAll(anyLong())).thenReturn(new ArrayList<>());
    TestUtils.testGetRequest(mockMvc, "/category/1/subcategories");
  }

  @Test
  void findById() {
    when(subCategoryService.findById(anyLong())).thenReturn(new SubCategoryResponse());
    TestUtils.testGetRequest(mockMvc, "/subcategory/1");
  }

  @Test
  void save() {
    when(subCategoryService.save(any(SubCategoryRequest.class)))
        .thenReturn(new DevOpsSubcategory());
    TestUtils.testPostRequest(
        mockMvc,
        "/subcategory",
        SubCategoryRequest.builder().description("description").devOpsCategoryId(1L).build());
  }

  @Test
  void update() {
    when(subCategoryService.update(any(SubCategoryRequest.class)))
        .thenReturn(new DevOpsSubcategory());
    TestUtils.testPutRequest(
        mockMvc,
        "/subcategory",
        SubCategoryRequest.builder().description("description").devOpsCategoryId(1L).build());
  }

  @Test
  void toggleActive() {
    doNothing().when(subCategoryService).toggleActive(anyLong());
    TestUtils.testPatchRequest(mockMvc, "/subcategory/1", null);
  }
}
