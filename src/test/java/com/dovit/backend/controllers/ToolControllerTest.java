package com.dovit.backend.controllers;

import com.dovit.backend.domain.Tool;
import com.dovit.backend.model.requests.ToolRequest;
import com.dovit.backend.model.responses.ToolResponse;
import com.dovit.backend.services.ToolServiceImpl;
import com.dovit.backend.util.RequestBuilderUtil;
import com.dovit.backend.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

/**
 * @author Ramón París
 * @since 30-03-20
 */
@ExtendWith(SpringExtension.class)
class ToolControllerTest {

  @InjectMocks ToolController toolController;
  @Mock ToolServiceImpl toolService;
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(toolController).build();
  }

  @Test
  void findAll() {
    Mockito.when(toolService.findAllTools()).thenReturn(new ArrayList<>());
    TestUtils.testGetRequest(mockMvc, "/tools");
  }

  @Test
  void findAllToolsByCompany() {
    Mockito.when(toolService.findAllToolsOfCompany(anyLong())).thenReturn(new ArrayList<>());
    TestUtils.testGetRequest(mockMvc, "/company/1/tools");
  }

  @Test
  void findById() {
    Mockito.when(toolService.findById(anyLong())).thenReturn(new ToolResponse());
    TestUtils.testGetRequest(mockMvc, "/tool/1");
  }

  @Test
  void save() {
    Mockito.when(toolService.save(any(ToolRequest.class))).thenReturn(new Tool());
    ToolRequest request = RequestBuilderUtil.getToolRequest();
    TestUtils.testPostRequest(mockMvc, "/tool", request);
  }
}
