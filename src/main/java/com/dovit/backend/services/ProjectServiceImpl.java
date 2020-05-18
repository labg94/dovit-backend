package com.dovit.backend.services;

import com.dovit.backend.domain.DevOpsCategory;
import com.dovit.backend.domain.Member;
import com.dovit.backend.domain.Project;
import com.dovit.backend.domain.ProjectMember;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.requests.ProjectRequest;
import com.dovit.backend.model.responses.MemberResponse;
import com.dovit.backend.model.responses.ProjectMemberRecommendation;
import com.dovit.backend.model.responses.ProjectResponse;
import com.dovit.backend.repositories.DevOpsCategoryRepository;
import com.dovit.backend.repositories.MemberRepository;
import com.dovit.backend.repositories.ProjectMemberRepository;
import com.dovit.backend.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Value;
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

  @Value("${api.image.route}")
  private String BASE_IMAGE_URL;

  @Override
  @Transactional
  public Project saveProject(ProjectRequest request) {
    request.setId(null);
    Project project = modelMapper.map(request, Project.class);
    //        project.setStart(request.getStart().toInstant());
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

    return modelMapper.map(project, ProjectResponse.class);
  }

  @Override
  @Transactional
  public List<ProjectMemberRecommendation> findMemberRecommendation(Long companyId) {
    List<DevOpsCategory> devOpsCategories = devOpsCategoryRepository.findAll();
    List<Member> members = memberRepository.findAllByCompanyId(companyId);
    ModelMapper modelMapper = new ModelMapper();
    List<ProjectMemberRecommendation> recommendations =
        devOpsCategories.stream()
            .map(d -> modelMapper.map(d, ProjectMemberRecommendation.class))
            .collect(Collectors.toList());

    // TODO optimizar este código desde una query en la base de datos!
    return recommendations.stream()
        .peek(
            r -> {
              List<MemberResponse> membersRecommendation =
                  members.stream()
                      .filter(
                          m ->
                              m.getToolProfile().stream()
                                  .anyMatch(
                                      toolProfile ->
                                          toolProfile.getTool().getSubcategories().stream()
                                              .anyMatch(
                                                  sub ->
                                                      sub.getDevOpsCategory()
                                                          .getId()
                                                          .equals(r.getDevOpsCategoryId()))))
                      .map(
                          m ->
                              modelMapper.map(
                                  m,
                                  MemberResponse
                                      .class)) // TODO esto no está funcionando bien. Se debe
                      // revisar el mapper para llenar las tools.
                      .collect(Collectors.toList());

              r.setMembersRecommendation(membersRecommendation);
            })
        .sorted(Comparator.comparing(ProjectMemberRecommendation::getDevOpsCategoryId))
        .collect(Collectors.toList());
  }
}
