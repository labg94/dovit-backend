package com.dovit.backend.services;

import com.dovit.backend.domain.ProjectType;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.payloads.requests.ProjectTypeRequest;
import com.dovit.backend.payloads.responses.MasterRegistryResponse;
import com.dovit.backend.repositories.ProjectTypeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 31-05-20
 */
@Service
@RequiredArgsConstructor
public class ProjectTypeServiceImpl implements ProjectTypeService {

  private final ProjectTypeRepository projectTypeRepository;
  private final ModelMapper modelMapper;

  @Override
  public List<MasterRegistryResponse> findAll() {
    List<ProjectType> projectTypes = projectTypeRepository.findAll();
    return projectTypes.stream()
        .map(projectType -> modelMapper.map(projectType, MasterRegistryResponse.class))
        .collect(Collectors.toList());
  }

  @Override
  public ProjectType save(ProjectTypeRequest request) {
    request.setId(null);
    ProjectType projectType = modelMapper.map(request, ProjectType.class);
    return projectTypeRepository.save(projectType);
  }

  @Override
  public ProjectType update(ProjectTypeRequest request) {
    ProjectType projectType =
        projectTypeRepository
            .findById(request.getId())
            .orElseThrow(() -> new ResourceNotFoundException("ProjectType", "id", request.getId()));

    modelMapper.map(request, projectType);
    return projectTypeRepository.save(projectType);
  }
}
