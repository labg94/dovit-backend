package com.dovit.backend.services;

import com.dovit.backend.config.ModelMapperConfig;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.responses.ProjectResponse;
import com.dovit.backend.repositories.DevOpsCategoryRepository;
import com.dovit.backend.repositories.MemberRepository;
import com.dovit.backend.repositories.ProjectMemberRepository;
import com.dovit.backend.repositories.ProjectRepository;
import org.junit.jupiter.api.AfterEach;
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

import static com.dovit.backend.util.DomainBuilderUtil.project;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * @author Ramón París
 * @since 07-05-20
 */
@ExtendWith(SpringExtension.class)
class ProjectServiceImplTest {

  @InjectMocks ProjectServiceImpl projectService;
  @Mock ProjectMemberRepository projectMemberRepository;
  @Mock ProjectRepository projectRepository;
  @Mock MemberRepository memberRepository;
  @Mock DevOpsCategoryRepository devOpsCategoryRepository;
  @Spy ModelMapper modelMapper = new ModelMapperConfig().modelMapper();

  @AfterEach
  void tearDown(TestInfo info) {
    if (!info.getTags().contains("SkipAfter")) modelMapper.validate();
  }

  @Test
  void testSaveProject() {}

  @Test
  void testUpdateProject() {}

  @Test
  void testFindAllByCompanyId() {
    when(projectRepository.findAllByCompanyId(anyLong())).thenReturn(List.of(project));
    List<ProjectResponse> responses = projectService.findAllByCompanyId(1L);
    assertNotNull(responses);
    responses.forEach(r -> assertNotNull(responses));
  }

  @Test
  void testFindByProjectId_OK() {
    when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
    ProjectResponse response = projectService.findByProjectId(1L);
    assertNotNull(response);
  }

  @Test
  void testFindByProjectId_EXCEPTION() {
    when(projectRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> projectService.findByProjectId(1L));
  }

  @Test
  void testFindMemberRecommendation() {}
}
