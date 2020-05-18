package com.dovit.backend.controllers;

import com.dovit.backend.domain.DevOpsCategory;
import com.dovit.backend.model.requests.CategoryRequest;
import com.dovit.backend.model.responses.CategoryResponse;
import com.dovit.backend.services.DevOpsCategoryServiceImpl;
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
class DevOpsCategoryControllerTest {

  @InjectMocks DevOpsCategoryController devOpsCategoryController;
  @Mock DevOpsCategoryServiceImpl devOpsCategoryService;
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(devOpsCategoryController).build();
  }

  @Test
  void findAllActives() {
    when(devOpsCategoryService.findAllActives()).thenReturn(new ArrayList<>());
    TestUtils.testGetRequest(mockMvc, "/devOpsCategories/actives");
  }

  @Test
  void findAll() {
    when(devOpsCategoryService.findAll()).thenReturn(new ArrayList<>());
    TestUtils.testGetRequest(mockMvc, "/devOpsCategories");
  }

  @Test
  void findById() {
    when(devOpsCategoryService.findById(anyLong())).thenReturn(new CategoryResponse());
    TestUtils.testGetRequest(mockMvc, "/devOpsCategory/1");
  }

  @Test
  void save() {
    when(devOpsCategoryService.save(any(CategoryRequest.class))).thenReturn(new DevOpsCategory());
    TestUtils.testPostRequest(
        mockMvc, "/devOpsCategory", CategoryRequest.builder().description("description").build());
  }

  @Test
  void update() {
    when(devOpsCategoryService.update(any(CategoryRequest.class))).thenReturn(new DevOpsCategory());
    TestUtils.testPutRequest(
        mockMvc, "/devOpsCategory", CategoryRequest.builder().description("description").build());
  }

  @Test
  void toggleActive() {
    doNothing().when(devOpsCategoryService).toggleActive(anyLong());
    TestUtils.testPatchRequest(mockMvc, "/devOpsCategory/1", null);
  }
}
