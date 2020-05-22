package com.dovit.backend.services;

import com.dovit.backend.domain.*;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.requests.ProjectMemberRequest;
import com.dovit.backend.model.requests.ProjectRequest;
import com.dovit.backend.model.responses.MemberResponseDetail;
import com.dovit.backend.model.responses.ProjectMemberRecommendation;
import com.dovit.backend.model.responses.ProjectResponse;
import com.dovit.backend.repositories.DevOpsCategoryRepository;
import com.dovit.backend.repositories.MemberRepository;
import com.dovit.backend.repositories.ProjectMemberRepository;
import com.dovit.backend.repositories.ProjectRepository;
import com.dovit.backend.util.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  @Override
  @Transactional
  public Project saveProject(ProjectRequest request) {
    final List<Long> membersId =
        request.getMembers().stream()
            .map(ProjectMemberRequest::getMemberId)
            .collect(Collectors.toList());

    memberService.areMembersInCompany(membersId, request.getCompanyId());
    request.setId(null);
    Project project = modelMapper.map(request, Project.class);
    project = projectRepository.save(project);

    Long projectId = project.getId();
    project.getMembers().forEach(m -> m.setProjectId(projectId));
    projectMemberRepository.saveAll(project.getMembers());

    return project;
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
  public List<ProjectResponse> findAllByCompanyId(Long companyId) {
    validatorUtil.canActOnCompany(companyId);
    List<Project> projects = projectRepository.findAllByCompanyId(companyId);
    return projects.stream()
        .map(p -> modelMapper.map(p, ProjectResponse.class))
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
    return modelMapper.map(project, ProjectResponse.class);
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
