package com.dovit.backend.services;

import com.dovit.backend.domain.DevOpsSubcategory;
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
  private final DevOpsSubCategoryService subCategoryService;

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
    Tool t = findToolById(toolId);

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
    tool.setActive(true);
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

  @Override
  @Transactional
  public Tool update(ToolRequest request) {
    Tool tool = findToolById(request.getId());

    // Should not update licenses
    request.setLicenses(null);
    modelMapper.map(request, tool);
    return toolRepository.save(tool);
  }

  @Override
  public void toggleActive(Long toolId) {
    Tool tool = findToolById(toolId);
    tool.setActive(!tool.isActive());
    toolRepository.save(tool);
  }

  @Override
  @Transactional
  public List<ToolResponse> findAllActiveTools() {
    List<Tool> tools = toolRepository.findAllByActive(true);
    return tools.stream()
        .map(tool -> modelMapper.map(tool, ToolResponse.class))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public List<ToolResponse> findAllActiveBySubcategory(Long subcategoryId) {
    DevOpsSubcategory subcategory = subCategoryService.findById(subcategoryId);
    List<Tool> tools = toolRepository.findAllByActiveAndSubcategoriesContains(true, subcategory);
    return tools.stream()
        .map(tool -> modelMapper.map(tool, ToolResponse.class))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public List<ToolResponse> findAllBySubcategory(Long subcategoryId) {
    DevOpsSubcategory subcategory = subCategoryService.findById(subcategoryId);
    List<Tool> tools = toolRepository.findAllBySubcategoriesContains(subcategory);
    return tools.stream()
        .map(tool -> modelMapper.map(tool, ToolResponse.class))
        .collect(Collectors.toList());
  }

  private Tool findToolById(Long toolId) {
    return toolRepository
        .findById(toolId)
        .orElseThrow(() -> new ResourceNotFoundException("Tool", "id", toolId));
  }
}
