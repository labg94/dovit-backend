package com.dovit.backend.services;

import com.dovit.backend.domain.License;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.responses.CompanyLicensesResponse;
import com.dovit.backend.repositories.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ramón París
 * @since 01-10-2019
 */
@Service
public class LicenseServiceImpl implements LicenseService {

    @Autowired
    private LicenseRepository licenseRepository;

    @Override
    public List<CompanyLicensesResponse> findAllByCompanyId(Long companyId) {
        return null;
    }

    @Override
    public List<CompanyLicensesResponse> findAllLicencesOfToolByCompanyId(Long toolId, Long companyId) {
        return null;
    }

    @Override
    public License findById(Long licenseId) {
        return licenseRepository.findById(licenseId).orElseThrow(()->new ResourceNotFoundException("License", "id", licenseId));
    }
}
