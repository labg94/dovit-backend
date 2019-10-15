package com.dovit.backend.services;

import com.dovit.backend.domain.License;
import com.dovit.backend.model.responses.CompanyLicensesResponse;

import java.util.List;

/**
 * @author Ramón París
 * @since 01-10-2019
 */
public interface LicenseService {

    List<CompanyLicensesResponse> findAllByCompanyId(Long companyId);

    List<CompanyLicensesResponse> findAllLicencesOfToolByCompanyId(Long toolId, Long companyId);

    License findById(Long licenseId);





}
