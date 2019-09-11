package com.dovit.backend.service;

import com.dovit.backend.domain.Company;

import java.util.List;

public interface CompanyService {

    Company findById(Long id);

    List<Company> findAll();

    Company updateCompany(Company company);
}
