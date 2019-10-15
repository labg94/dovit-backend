package com.dovit.backend.services;

import com.dovit.backend.model.responses.CompanyLicensesResponse;

import java.util.List;

/**
 * @author Ramón París
 * @since 14-10-2019
 */
public interface CompanyLicenseService {

    List<CompanyLicensesResponse> findAllByCompanyIdAndToolId(Long companyId, Long toolId);

}
