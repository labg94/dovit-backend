package com.dovit.backend.services;

import com.dovit.backend.domain.ProjectType;
import com.dovit.backend.model.responses.MasterRegistryResponse;
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
}
