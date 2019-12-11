package com.dovit.backend.services;

import com.dovit.backend.domain.Project;
import com.dovit.backend.domain.ProjectMember;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.requests.ProjectRequest;
import com.dovit.backend.model.responses.MemberParticipationResponse;
import com.dovit.backend.model.responses.ProjectMemberResponse;
import com.dovit.backend.model.responses.ProjectResponse;
import com.dovit.backend.model.responses.ToolProfileResponse;
import com.dovit.backend.repositories.CustomRepository;
import com.dovit.backend.repositories.ProjectMemberRepository;
import com.dovit.backend.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final CustomRepository customRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    @Transactional
    public Project saveProject(ProjectRequest request) {
        Project project = modelMapper.map(request, Project.class);
        project.setStart(request.getStart().toInstant());
        project = projectRepository.save(project);

        Long projectId = project.getId();
        project.getMembers().forEach(m -> m.setProjectId(projectId));
        projectMemberRepository.saveAll(project.getMembers());

        return project;
    }

    @Override
    @Transactional
    public Project updateProject(ProjectRequest request) {
        Project project = projectRepository.findById(request.getId()).orElseThrow(()->new ResourceNotFoundException("Project", "id", request.getId()));
        projectMemberRepository.deleteAllByProjectId(request.getId());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setCollectionsMergeEnabled(false);

        PropertyMap<ProjectRequest, Project> skipModelMapper = new PropertyMap<ProjectRequest, Project>() {
            protected void configure() {
                skip().setMembers(null);
            }
        };

        modelMapper.addMappings(skipModelMapper);
        modelMapper.map(request, project);

        project = projectRepository.save(project);

        List<ProjectMember> members = request.getMembers().stream().map(m->new ModelMapper().map(m, ProjectMember.class)).collect(Collectors.toList());
        members.forEach(m->{
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
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setAmbiguityIgnored(true);
        List<ProjectResponse> responses = projects.stream().map(p -> mapper.map(p, ProjectResponse.class)).collect(Collectors.toList());
        return responses;
    }

    @Transactional
    @Override
    public ProjectResponse findByProjectId(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setAmbiguityIgnored(true);
        ProjectResponse response = mapper.map(project, ProjectResponse.class);
        //TODO mejorar este código de mierda xd.. no programen de madrugada :)
        List<ProjectMemberResponse> members = project
                .getMembers()
                .stream().collect(Collectors.groupingBy(p -> p.getMember())).keySet().stream().map(m -> {
                    ProjectMemberResponse memberResponse = new ProjectMemberResponse();
                    memberResponse.setMemberId(m.getId());
                    memberResponse.setMemberName(m.getName());
                    memberResponse.setMemberLastName(m.getLastName());
                    return memberResponse;
                }).collect(Collectors.toList());

        members.forEach(m -> {
            List<MemberParticipationResponse> participations = project.getMembers().stream().filter(m1 -> m1.getMemberId().equals(m.getMemberId())).map(m2 -> {
                MemberParticipationResponse participation = new MemberParticipationResponse();
                participation.setDevopsCategoryName(m2.getDevOpsCategories().getDescription());

                List<ToolProfileResponse>
                        toolsOfDevOpsCat = m2.getMember()
                        .getToolProfile()
                        .stream()
                        .filter(toolProfile -> toolProfile.getTool()
                                .getSubcategories()
                                .stream()
                                .collect(Collectors.groupingBy(sub -> sub.getDevOpsCategory().getId())).containsKey(m2.getDevOpsCategoryId()))
                        .map(ToolProfileResponse::new)
                        .collect(Collectors.toList());

                participation.setTools(toolsOfDevOpsCat);
                return participation;
            }).collect(Collectors.toList());
            m.setParticipation(participations);
        });

        response.setMembers(members);
        return response;
    }
}
