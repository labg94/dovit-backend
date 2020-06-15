package com.dovit.backend.services;

import com.dovit.backend.domain.*;
import com.dovit.backend.exceptions.BadRequestException;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.payloads.requests.PipelineToolRequest;
import com.dovit.backend.payloads.requests.ProjectMemberRequest;
import com.dovit.backend.payloads.requests.ProjectRequest;
import com.dovit.backend.payloads.responses.*;
import com.dovit.backend.repositories.*;
import com.dovit.backend.util.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 09-12-2019
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

  private final ProjectRepository projectRepository;
  private final ProjectMemberRepository projectMemberRepository;
  private final MemberRepository memberRepository;
  private final DevOpsCategoryRepository devOpsCategoryRepository;
  private final ModelMapper modelMapper;
  private final ValidatorUtil validatorUtil;
  private final MemberService memberService;
  private final PipelineService pipelineService;
  private final PipelineToolRepository pipelineToolRepository;

  @Override
  @Transactional
  public Project saveProject(ProjectRequest request) {
    checkAllCategoriesSelected(request.getSelectedTools());
    final List<Long> membersId =
        request.getMembers().stream()
            .map(ProjectMemberRequest::getMemberId)
            .collect(Collectors.toList());

    memberService.areMembersInCompany(membersId, request.getCompanyId());
    request.setId(null);

    Project project = modelMapper.map(request, Project.class);
    project.setPipelines(
        Arrays.asList(
            pipelineService.createSelectedPipeline(project, request.getSelectedTools()),
            pipelineService.createRecommendedPipeline(project, request)));

    project = projectRepository.save(project);

    project
        .getPipelines()
        .forEach(
            pipeline -> {
              pipeline
                  .getPipelineTools()
                  .forEach(pipelineTool -> pipelineTool.setPipelineId(pipeline.getId()));
              pipelineToolRepository.saveAll(pipeline.getPipelineTools());
            });

    Long projectId = project.getId();
    project.getMembers().forEach(m -> m.setProjectId(projectId));
    projectMemberRepository.saveAll(project.getMembers());

    return project;
  }

  private void checkAllCategoriesSelected(List<PipelineToolRequest> toolsSelected) {
    List<DevOpsCategory> devOpsCategories = devOpsCategoryRepository.findAllByActiveOrderById(true);
    List<Long> selectedCategoriesId =
        toolsSelected.stream().map(PipelineToolRequest::getCategoryId).collect(Collectors.toList());
    boolean allMatch =
        devOpsCategories.stream()
            .allMatch(category -> selectedCategoriesId.contains(category.getId()));

    if (!allMatch) {
      throw new BadRequestException("You have not selected a tool for all categories");
    }

    boolean hasSingleTool =
        toolsSelected.stream()
            .collect(Collectors.groupingBy(PipelineToolRequest::getCategoryId))
            .entrySet()
            .stream()
            .allMatch(entry -> entry.getValue().size() == 1);

    if (!hasSingleTool) {
      throw new BadRequestException("A category does not have one tool selected");
    }
  }

  @Override
  @Transactional
  public Project updateProject(ProjectRequest request) {
    Project project =
        projectRepository
            .findById(request.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Project", "id", request.getId()));
    projectMemberRepository.deleteAllByProjectId(request.getId());
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setCollectionsMergeEnabled(false);

    PropertyMap<ProjectRequest, Project> skipModelMapper =
        new PropertyMap<>() {
          protected void configure() {
            skip().setMembers(null);
          }
        };

    modelMapper.addMappings(skipModelMapper);
    modelMapper.map(request, project);

    project = projectRepository.save(project);

    List<ProjectMember> members =
        request.getMembers().stream()
            .map(m -> new ModelMapper().map(m, ProjectMember.class))
            .collect(Collectors.toList());
    members.forEach(
        m -> {
          m.setProject(null);
          m.setProjectId(request.getId());
        });
    projectMemberRepository.saveAll(members);
    return project;
  }

  @Override
  @Transactional
  public List<ProjectResumeResponse> findAllByCompanyId(Long companyId) {
    validatorUtil.canActOnCompany(companyId);
    List<Project> projects = projectRepository.findAllByCompanyId(companyId);
    return projects.stream()
        .map(p -> modelMapper.map(p, ProjectResumeResponse.class))
        .collect(Collectors.toList());
  }

  @Transactional
  @Override
  public ProjectResponse findByProjectId(Long projectId) {
    Project project =
        projectRepository
            .findById(projectId)
            .orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));

    validatorUtil.canActOnCompany(project.getCompany().getId());

    final ProjectResponse projectResponse = modelMapper.map(project, ProjectResponse.class);

    // selected pipeline
    project.getPipelines().stream()
        .filter(pipeline -> !pipeline.isRecommended())
        .findFirst()
        .ifPresent(
            selectedPipeline ->
                projectResponse.setSelectedPipeline(
                    modelMapper.map(selectedPipeline, SelectedPipelineResponse.class)));

    // recommended pipeline
    project.getPipelines().stream()
        .filter(Pipeline::isRecommended)
        .findFirst()
        .ifPresent(
            recommendedPipeline ->
                projectResponse.setRecommendedPipeline(
                    modelMapper.map(recommendedPipeline, PipelineRecommendationResponse.class)));

    return projectResponse;
  }

  @Override
  @Transactional
  public List<ProjectMemberRecommendation> findMemberRecommendation(Long companyId) {
    List<DevOpsCategory> devOpsCategories = devOpsCategoryRepository.findAll();
    List<Member> members = memberRepository.findAllByCompanyId(companyId);
    return devOpsCategories.stream()
        .map(d -> modelMapper.map(d, ProjectMemberRecommendation.class))
        .peek(
            recommendation -> {
              List<MemberResponseDetail> membersRecommendation =
                  members.stream()
                      .filter(
                          member ->
                              memberKnowsCategory(recommendation.getDevOpsCategoryId(), member))
                      .map( // returns members with tools that match with actual category
                          member ->
                              member
                                  .toBuilder()
                                  .toolProfile(
                                      findToolsByCategory(
                                          recommendation.getDevOpsCategoryId(), member))
                                  .build())
                      .map(member -> modelMapper.map(member, MemberResponseDetail.class))
                      .peek(memberResponseDetail -> memberResponseDetail.setProjects(null))
                      .collect(Collectors.toList());

              recommendation.setMembersRecommendation(membersRecommendation);
            })
        .sorted(Comparator.comparing(ProjectMemberRecommendation::getDevOpsCategoryId))
        .collect(Collectors.toList());
  }

  private List<ToolProfile> findToolsByCategory(Long categoryId, Member member) {
    return member.getToolProfile().stream()
        .filter(t -> toolInCategory(t, categoryId))
        .collect(Collectors.toList());
  }

  private boolean toolInCategory(ToolProfile tool, Long devOpsCategoryId) {
    return tool.getTool().getSubcategories().stream()
        .anyMatch(sub -> sub.getDevOpsCategory().getId().equals(devOpsCategoryId));
  }

  private boolean memberKnowsCategory(Long categoryId, Member member) {
    return member.getToolProfile().stream()
        .anyMatch(toolProfile -> toolInCategory(toolProfile, categoryId));
  }
}
