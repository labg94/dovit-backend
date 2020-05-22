package com.dovit.backend.services;

import com.dovit.backend.config.ModelMapperConfig;
import com.dovit.backend.domain.Project;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.requests.ProjectMemberRequest;
import com.dovit.backend.model.requests.ProjectRequest;
import com.dovit.backend.model.responses.ProjectResponse;
import com.dovit.backend.repositories.DevOpsCategoryRepository;
import com.dovit.backend.repositories.MemberRepository;
import com.dovit.backend.repositories.ProjectMemberRepository;
import com.dovit.backend.repositories.ProjectRepository;
import com.dovit.backend.util.ValidatorUtil;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.dovit.backend.util.DomainBuilderUtil.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
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
  @Mock MemberServiceImpl memberService;
  @Mock ValidatorUtil validatorUtil;
  @Spy ModelMapper modelMapper = new ModelMapperConfig().modelMapper();

  @AfterEach
  void tearDown(TestInfo info) {
    if (!info.getTags().contains("SkipAfter")) modelMapper.validate();
  }

  @Test
  @Tag("SkipAfter")
  void testSaveProject() {
    ProjectRequest projectRequest =
        ProjectRequest.builder()
            .id(1L)
            .companyId(1L)
            .finished(true)
            .name("BOSS")
            .observation("Observacion")
            .start(LocalDate.now())
            .members(
                Arrays.asList(
                    ProjectMemberRequest.builder().memberId(1L).devOpsCategoryId(6L).build(),
                    ProjectMemberRequest.builder().memberId(2L).devOpsCategoryId(7L).build(),
                    ProjectMemberRequest.builder().memberId(3L).devOpsCategoryId(8L).build(),
                    ProjectMemberRequest.builder().memberId(4L).devOpsCategoryId(9L).build()))
            .build();

    when(projectRepository.save(any(Project.class))).thenAnswer(i -> i.getArgument(0));
    when(projectMemberRepository.saveAll(any())).thenAnswer(i -> i.getArgument(0));
    doNothing().when(memberService).areMembersInCompany(anyList(), anyLong());

    Project response = projectService.saveProject(projectRequest);
    assertNotNull(response);
  }

  @Test
  void testUpdateProject() {}

  @Test
  void testFindAllByCompanyId() {
    when(projectRepository.findAllByCompanyId(anyLong())).thenReturn(List.of(project));
    when(validatorUtil.canActOnCompany(anyLong())).thenReturn(true);
    List<ProjectResponse> responses = projectService.findAllByCompanyId(1L);
    assertNotNull(responses);
    responses.forEach(r -> assertNotNull(responses));
  }

  @Test
  void testFindByProjectId_OK() {
    when(projectRepository.findById(anyLong())).thenReturn(Optional.of(project));
    when(validatorUtil.canActOnCompany(anyLong())).thenReturn(true);
    ProjectResponse response = projectService.findByProjectId(1L);
    assertNotNull(response);
  }

  @Test
  void testFindByProjectId_EXCEPTION() {
    when(projectRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> projectService.findByProjectId(1L));
  }

  @Test
  void testFindMemberRecommendation() {
    when(devOpsCategoryRepository.findAll())
        .thenReturn(Arrays.asList(devOpsCategoryPlanning, devOpsCategoryCICD));

    when(memberRepository.findAllByCompanyId(anyLong()))
        .thenReturn(Collections.singletonList(member));

    //    List<ProjectMemberRecommendation>
  }
}
