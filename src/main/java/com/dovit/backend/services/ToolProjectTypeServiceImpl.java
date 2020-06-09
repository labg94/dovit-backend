package com.dovit.backend.services;

import com.dovit.backend.domain.ToolProjectType;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.requests.ToolProjectTypeRequest;
import com.dovit.backend.payloads.responses.ProjectTypeResponse;
import com.dovit.backend.repositories.ToolProjectTypeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 08-06-20
 */
@Service
@RequiredArgsConstructor
public class ToolProjectTypeServiceImpl implements ToolProjectTypeService {

  private final ToolProjectTypeRepository toolProjectTypeRepository;
  private final ModelMapper modelMapper;

  @Override
  public List<ProjectTypeResponse> findAllByToolId(Long toolId) {
    List<ToolProjectType> toolProjectTypes = toolProjectTypeRepository.findAllByToolId(toolId);
    return toolProjectTypes.stream()
        .map(toolProjectType -> modelMapper.map(toolProjectType, ProjectTypeResponse.class))
        .collect(Collectors.toList());
  }

  @Override
  public void save(ToolProjectTypeRequest toolProjectTypeRequest) {
    ToolProjectType toolProjectType =
        modelMapper.map(toolProjectTypeRequest, ToolProjectType.class);
    toolProjectTypeRepository.save(toolProjectType);
  }

  @Override
  public void delete(Long toolId, Long projectTypeId) {
    ToolProjectType toolProjectType =
        toolProjectTypeRepository
            .findAllByToolIdAndProjectTypeId(toolId, projectTypeId)
            .orElseThrow(() -> new ResourceNotFoundException("Tool - Project Type", "", ""));
    toolProjectTypeRepository.delete(toolProjectType);
  }
}
