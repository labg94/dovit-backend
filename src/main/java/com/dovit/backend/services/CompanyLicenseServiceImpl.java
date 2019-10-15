package com.dovit.backend.services;

import com.dovit.backend.domain.CompanyLicense;
import com.dovit.backend.model.responses.CompanyLicensesResponse;
import com.dovit.backend.repositories.CompanyLicenseRepository;
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

    @Override
    public List<CompanyLicensesResponse> findAllByCompanyIdAndToolId(Long companyId, Long toolId) {
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
}
