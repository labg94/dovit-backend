package com.dovit.backend.services;

import com.dovit.backend.domain.DevOpsCategory;
import com.dovit.backend.domain.DevOpsSubcategory;
import com.dovit.backend.domain.Tool;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.responses.ToolResponse;
import com.dovit.backend.repositories.ToolRepository;
import com.dovit.backend.util.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Ramón París
 * @since 01-10-2019
 */
@Service
@RequiredArgsConstructor
public class ToolServiceImpl implements ToolService {

  private final ToolRepository toolRepository;
  private final ValidatorUtil validatorUtil;
  private final ModelMapper modelMapper;

  @Value("${api.image.route}")
  private String BASE_IMAGE_URL;

  @Override
  @Transactional
  public List<ToolResponse> findAllToolsOfCompany(Long companyId) {
    validatorUtil.canActOnCompany(companyId);
    List<Tool> tools = toolRepository.findAllByCompanyId(companyId);
    return tools.stream().map(this::mapToolResponse).collect(Collectors.toList());
  }

  @Override
  @Transactional
  public List<ToolResponse> findAllTools() {
    List<Tool> tools = toolRepository.findAll();
    return tools.stream().map(this::mapToolResponse).collect(Collectors.toList());
  }

  @Override
  @Transactional
  public ToolResponse findById(Long toolId) {
    Tool t =
        toolRepository
            .findById(toolId)
            .orElseThrow(() -> new ResourceNotFoundException("id", "Tool", toolId));

    return this.mapToolResponse(t);
  }

  private ToolResponse mapToolResponse(Tool tool) {
    ToolResponse toolResponse = modelMapper.map(tool, ToolResponse.class);
    toolResponse.setImageUrl(BASE_IMAGE_URL + toolResponse.getImageUrl());

    List<String> tags =
        tool.getSubcategories().stream()
            .map(DevOpsSubcategory::getDescription)
            .collect(Collectors.toList());

    List<String> parentTags =
        tool.getSubcategories().stream()
            .map(DevOpsSubcategory::getDevOpsCategory)
            .map(DevOpsCategory::getDescription)
            .distinct()
            .collect(Collectors.toList());

    toolResponse.setTags(
        Stream.concat(parentTags.stream(), tags.stream()).collect(Collectors.toList()));

    return toolResponse;
  }
}
