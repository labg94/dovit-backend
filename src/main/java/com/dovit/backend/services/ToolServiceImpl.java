package com.dovit.backend.services;

import com.dovit.backend.domain.Tool;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.requests.ToolRequest;
import com.dovit.backend.model.responses.ToolResponse;
import com.dovit.backend.repositories.ToolRepository;
import com.dovit.backend.util.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

  @Override
  @Transactional
  public List<ToolResponse> findAllToolsOfCompany(Long companyId) {
    validatorUtil.canActOnCompany(companyId);
    List<Tool> tools = toolRepository.findAllByCompanyId(companyId);
    return tools.stream()
        .map(tool -> modelMapper.map(tool, ToolResponse.class))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public List<ToolResponse> findAllTools() {
    List<Tool> tools = toolRepository.findAll();
    return tools.stream()
        .map(tool -> modelMapper.map(tool, ToolResponse.class))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public ToolResponse findById(Long toolId) {
    Tool t =
        toolRepository
            .findById(toolId)
            .orElseThrow(() -> new ResourceNotFoundException("id", "Tool", toolId));

    return modelMapper.map(t, ToolResponse.class);
  }

  @Override
  @Transactional
  public Tool save(ToolRequest request) {
    request.setId(null);
    request
        .getLicenses()
        .forEach(
            l -> {
              l.setLicenseId(null);
              l.getLicensePrices()
                  .forEach(licensePricingRequest -> licensePricingRequest.setId(null));
            });

    final Tool tool = modelMapper.map(request, Tool.class);
    tool.getLicenses()
        .forEach(
            license -> {
              license.setTool(tool);
              license
                  .getLicensePrices()
                  .forEach(licensePricing -> licensePricing.setLicense(license));
            });
    return toolRepository.save(tool);
  }
}
