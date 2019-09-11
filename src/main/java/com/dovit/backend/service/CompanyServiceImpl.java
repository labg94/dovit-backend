package com.dovit.backend.service;

import com.dovit.backend.domain.Company;
import com.dovit.backend.repository.CompanyRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final AuditService auditService;

    @Override
    public Company findById(Long id) {
        return null;
    }

    @Override
    public List<Company> findAll() {
        return null;
    }

    @Override
    public Company updateCompany(Company company) {
        return null;
    }
}
