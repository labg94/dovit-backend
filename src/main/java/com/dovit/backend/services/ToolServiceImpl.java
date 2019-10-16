package com.dovit.backend.services;

import com.dovit.backend.domain.CompanyLicense;
import com.dovit.backend.domain.DevOpsCategory;
import com.dovit.backend.domain.DevOpsSubcategory;
import com.dovit.backend.domain.Tool;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.responses.DevopsCategoryResponse;
import com.dovit.backend.model.responses.DevopsSubCategoryResponse;
import com.dovit.backend.model.responses.ToolResponse;
import com.dovit.backend.repositories.CompanyLicenseRepository;
import com.dovit.backend.repositories.DevOpsCategoryRepository;
import com.dovit.backend.repositories.ToolRepository;
import com.dovit.backend.security.JwtAuthenticationFilter;
import com.dovit.backend.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 01-10-2019
 */
@Service
public class ToolServiceImpl implements ToolService {

    @Autowired
    private ToolRepository toolRepository;

    @Autowired
    private CompanyLicenseRepository companyLicenseRepository;

    @Autowired
    private DevOpsCategoryRepository devOpsCategoryRepository;

    @Value("${api.image.route}")
    private String BASE_IMAGE_URL;

    @Override
    public List<ToolResponse> findAllToolsOfCompany(Long companyId) {
        JwtAuthenticationFilter.canActOnCompany(companyId);
        List<Tool> tools = toolRepository.findAllByCompanyId(companyId);
        return ModelMapper.mapToolToResponse(tools, BASE_IMAGE_URL);
    }

    @Override
    @Transactional
    public List<DevopsCategoryResponse> findAllToolsGroupedByCats() {
        List<DevOpsCategory> categories = devOpsCategoryRepository.findAll();
        return ModelMapper.mapDevOpsCategoryToResponse(categories, BASE_IMAGE_URL);
    }

    @Override
    public List<ToolResponse> findAllTools() {
        List<Tool> tools = toolRepository.findAll();
        return ModelMapper.mapToolToResponse(tools, BASE_IMAGE_URL);
    }

    @Override
    public ToolResponse findById(Long toolId) {
        Tool t = toolRepository.findById(toolId).orElseThrow(()-> new ResourceNotFoundException("id", "Tool", toolId));
        ToolResponse toolResponse = new ToolResponse();
        toolResponse.setToolId(t.getId());
        toolResponse.setToolName(t.getName());
        toolResponse.setUrlImg(BASE_IMAGE_URL + t.getImageUrl());
        toolResponse.setTags(t.getSubcategories().stream().map(DevOpsSubcategory::getDescription).collect(Collectors.toList()));

        Set<String> parentTags = t.getSubcategories()
                .stream()
                .map(DevOpsSubcategory::getDevOpsCategory)
                .map(DevOpsCategory::getDescription)
                .collect(Collectors.toSet());

        toolResponse.getTags().addAll(parentTags);

        return toolResponse;
    }
}
