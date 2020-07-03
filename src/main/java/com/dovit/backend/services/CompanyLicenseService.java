package com.dovit.backend.services;

import com.dovit.backend.domain.CompanyLicense;
import com.dovit.backend.payloads.requests.CompanyLicenseRequest;
import com.dovit.backend.payloads.responses.CompanyLicensesResponse;

import java.util.List;

/**
 * @author Ramón París
 * @since 14-10-2019
 */
public interface CompanyLicenseService {

  List<CompanyLicensesResponse> findAllByCompanyIdAndToolId(Long companyId, Long toolId);

    CompanyLicense createCompanyLicense(CompanyLicenseRequest request);

    CompanyLicense updateCompanyLicense(CompanyLicenseRequest request);

    boolean deleteCompanyLicense(Long companyLicenseId);
}
