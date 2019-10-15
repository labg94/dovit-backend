package com.dovit.backend.services;

import com.dovit.backend.domain.Company;
import com.dovit.backend.domain.CompanyLicense;
import com.dovit.backend.domain.License;
import com.dovit.backend.model.requests.CompanyLicenseRequest;
import com.dovit.backend.model.responses.CompanyLicensesResponse;
import com.dovit.backend.repositories.CompanyLicenseRepository;
import com.dovit.backend.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 14-10-2019
 */
@Service
public class CompanyLicenseServiceImpl implements CompanyLicenseService {

    @Autowired
    private CompanyLicenseRepository companyLicenseRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private LicenseService licenseService;

    @Override
    public List<CompanyLicensesResponse> findAllByCompanyIdAndToolId(Long companyId, Long toolId) {
        JwtAuthenticationFilter.canActOnCompany(companyId);
        List<CompanyLicense> companyLicenses = companyLicenseRepository.findAllByCompanyIdAndLicenseToolId(companyId, toolId);
        return companyLicenses.stream().map(c -> {
            CompanyLicensesResponse response = new CompanyLicensesResponse();
            response.setLicenseId(c.getLicense().getId());
            response.setLicenseDesc(c.getLicense().getName());
            response.setObservation(c.getLicense().getObservation());
            response.setPayCycle(c.getLicense().getPayCycle());
            response.setLicenseTypeId(c.getLicense().getLicenseType().getId());
            response.setLicenseTypeDesc(c.getLicense().getLicenseType().getDescription());
            response.setCompanyLicenseId(c.getId());
            response.setStart(c.getStartDate());
            response.setExpiration(c.getExpirationDate());
            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public CompanyLicense createCompanyLicense(CompanyLicenseRequest request) {
        JwtAuthenticationFilter.canActOnCompany(request.getCompanyId());
        CompanyLicense companyLicense = new CompanyLicense();
        companyLicense.setStartDate(request.getStartDate().toInstant());
        if (request.getExpirationDate() != null) {
            companyLicense.setExpirationDate(request.getExpirationDate().toInstant());
        }

        Company company = companyService.findById(request.getCompanyId());
        companyLicense.setCompany(company);
        License license = licenseService.findById(request.getLicenseId());
        companyLicense.setLicense(license);
        companyLicense = companyLicenseRepository.save(companyLicense);
        return companyLicense;
    }
}
