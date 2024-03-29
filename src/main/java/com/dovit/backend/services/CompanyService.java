package com.dovit.backend.services;

import com.dovit.backend.domain.Company;
import com.dovit.backend.payloads.requests.CompanyRequest;
import com.dovit.backend.payloads.responses.CompanyResponse;

import java.util.List;

/**
 * @author Ramón París
 * @since 01-10-2019
 */
public interface CompanyService {

  Company findById(Long id);

  CompanyResponse findCompanyResponseById(Long id);

  List<CompanyResponse> findAll();

  Company createCompany(CompanyRequest companyRequest);

  Company updateCompany(CompanyRequest companyRequest);

  void toggleCompanyStatus(Long id);

  List<CompanyResponse> findAllActives();
}
