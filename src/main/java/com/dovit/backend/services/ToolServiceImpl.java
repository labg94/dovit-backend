package com.dovit.backend.services;

import com.dovit.backend.domain.CompanyLicense;
import com.dovit.backend.domain.DevOpsCategory;
import com.dovit.backend.domain.DevOpsSubcategory;
import com.dovit.backend.model.responses.DevopsCategoryResponse;
import com.dovit.backend.model.responses.DevopsSubCategoryResponse;
import com.dovit.backend.model.responses.ToolResponse;
import com.dovit.backend.repositories.CompanyLicenseRepository;
import com.dovit.backend.repositories.DevOpsCategoryRepository;
import com.dovit.backend.repositories.ToolRepository;
import com.dovit.backend.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public List<DevopsCategoryResponse> findAllToolsOfCompany(Long companyId) {
        List<DevOpsCategory> categories = devOpsCategoryRepository.findAllByCompanyId(companyId);
        return ModelMapper.mapDevOpsCategoryToResponse(categories);
    }

    @Override
    public List<DevopsCategoryResponse> findAllTools() {
        List<DevOpsCategory> categories = devOpsCategoryRepository.findAll();
        return ModelMapper.mapDevOpsCategoryToResponse(categories);
    }
}
