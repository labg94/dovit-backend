package com.dovit.backend.services;

import com.dovit.backend.payloads.requests.PipelineRecommendationRequest;
import com.dovit.backend.payloads.responses.PipelineResponse;
import com.dovit.backend.repositories.DevOpsCategoryRepository;
import com.dovit.backend.util.RequestBuilderUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static com.dovit.backend.util.DomainBuilderUtil.devOpsCategoryCICD;
import static com.dovit.backend.util.DomainBuilderUtil.devOpsCategoryPlanning;
import static com.dovit.backend.util.RecommendationBuilderUtil.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * @author Ramón París
 * @since 06-06-20
 */
@ExtendWith(MockitoExtension.class)
class PipelineServiceImplTest {

  @InjectMocks private PipelineServiceImpl pipelineService;
  @Mock private ToolServiceImpl toolService;
  @Mock private DevOpsCategoryRepository devOpsCategoryRepository;

  @Test
  void generatePipelineRecommendation() {
    when(devOpsCategoryRepository.findAllByActiveOrderById(anyBoolean()))
        .thenReturn(Arrays.asList(devOpsCategoryPlanning, devOpsCategoryCICD));

    // license mock
    when(toolService.findRecommendationByLicense(anyLong(), anyLong()))
        .thenReturn( // PLANNING
            Collections.singletonList(
                gitlab.toBuilder().points(Collections.singletonList(licensePoints)).build()))
        .thenReturn( // CI/CD
            Collections.singletonList(
                gitlab.toBuilder().points(Collections.singletonList(licensePoints)).build()));

    // project type mock
    when(toolService.findRecommendationByProjectType(anyLong(), anyList()))
        .thenReturn( // PLANNING
            Arrays.asList(
                gitlab.toBuilder().points(Collections.singletonList(projectTypePoints)).build(),
                azureDevOps
                    .toBuilder()
                    .points(Collections.singletonList(projectTypePoints))
                    .build(),
                jira.toBuilder().points(Collections.singletonList(projectTypePoints)).build()))
        .thenReturn( // CI/CD
            Arrays.asList(
                jenkins.toBuilder().points(Collections.singletonList(projectTypePoints)).build(),
                azureDevOps
                    .toBuilder()
                    .points(Collections.singletonList(projectTypePoints))
                    .build(),
                jira.toBuilder().points(Collections.singletonList(projectTypePoints)).build()));

    // member mock
    when(toolService.findRecommendationByMembers(anyLong(), anyList()))
        .thenReturn( // PLANNING
            Arrays.asList(
                gitlab.toBuilder().points(Collections.singletonList(memberPoints)).build(),
                azureDevOps.toBuilder().points(Collections.singletonList(memberPoints)).build()))
        .thenReturn( // CI/CD
            Arrays.asList(
                gitlab.toBuilder().points(Collections.singletonList(memberPoints)).build(),
                azureDevOps.toBuilder().points(Collections.singletonList(memberPoints)).build()));

    // history mock
    when(toolService.findRecommendationByProjectHistory(anyLong(), anyLong()))
        .thenReturn( // PLANNING
            Collections.singletonList(
                jira.toBuilder().points(Collections.singletonList(historyPoints)).build()))
        .thenReturn( // CI/CD
            Collections.singletonList(
                jenkins.toBuilder().points(Collections.singletonList(historyPoints)).build()));

    PipelineRecommendationRequest request =
        PipelineRecommendationRequest.builder()
            .budget(1000.0)
            .companyId(1L)
            .projectTypeIds(Arrays.asList(1L, 2L))
            .projectMembers(Collections.singletonList(RequestBuilderUtil.memberRequest))
            .build();

    final PipelineResponse pipelineResponse =
        pipelineService.generatePipelineRecommendation(request);
    System.out.println("asds");
  }

  @Test
  void findAllByProjectId() {}
}
