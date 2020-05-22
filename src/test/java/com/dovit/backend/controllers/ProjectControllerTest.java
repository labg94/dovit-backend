package com.dovit.backend.controllers;

import com.dovit.backend.domain.Project;
import com.dovit.backend.model.requests.ProjectMemberRequest;
import com.dovit.backend.model.requests.ProjectRequest;
import com.dovit.backend.model.responses.ProjectResponse;
import com.dovit.backend.services.ProjectServiceImpl;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

/**
 * @author Ramón París
 * @since 30-03-20
 */
@ExtendWith(SpringExtension.class)
class ProjectControllerTest {

  @InjectMocks ProjectController projectController;
  @Mock ProjectServiceImpl projectService;
  private MockMvc mockMvc;

  private final ProjectRequest request =
      ProjectRequest.builder()
          .name("Project")
          .start(LocalDate.now())
          .endDate(LocalDate.now())
          .companyId(1L)
          .finished(true)
          .members(
              Collections.singletonList(
                  ProjectMemberRequest.builder().memberId(1L).devOpsCategoryId(1L).build()))
          .build();
  private final Project project = Project.builder().id(1L).build();

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
  }

  @Test
  void findAllByCompanyId() {
    Mockito.when(projectService.findAllByCompanyId(anyLong())).thenReturn(new ArrayList<>());
    TestUtils.testGetRequest(mockMvc, "/company/1/projects");
  }

  @Test
  void findByProjectId() {
    Mockito.when(projectService.findByProjectId(anyLong())).thenReturn(new ProjectResponse());
    TestUtils.testGetRequest(mockMvc, "/project/1");
  }

  @Test
  void getMembersRecommendation() {
    Mockito.when(projectService.findMemberRecommendation(anyLong())).thenReturn(new ArrayList<>());
    TestUtils.testGetRequest(mockMvc, "/company/1/project/availableMembers");
  }

  @Test
  void createProject() {
    Mockito.when(projectService.saveProject(any(ProjectRequest.class))).thenReturn(project);
    TestUtils.testPostRequest(mockMvc, "/project", this.request);
  }

  @Test
  void updateProject() {
    Mockito.when(projectService.updateProject(any(ProjectRequest.class))).thenReturn(project);
    TestUtils.testPutRequest(mockMvc, "/project", this.request);
  }
}
