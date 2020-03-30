package com.dovit.backend.services;

import com.dovit.backend.domain.Company;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.requests.CompanyRequest;
import com.dovit.backend.model.responses.CompanyResponse;
import com.dovit.backend.repositories.CompanyRepository;
import com.dovit.backend.util.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 01-10-2019
 */
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

  private final CompanyRepository companyRepository;
  private final ValidatorUtil validatorUtil;

  @Override
  public Company findById(Long id) {
    return companyRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Company", "id", id));
  }

  @Override
  public CompanyResponse findCompanyResponseById(Long id) {
    validatorUtil.canActOnCompany(id);
    Company company = this.findById(id);
    return new CompanyResponse(company.getId(), company.getName());
  }

  @Override
  public List<CompanyResponse> findAll() {
    List<Company> companies = companyRepository.findAll();
    return companies.stream()
            .map(c -> new CompanyResponse(c.getId(), c.getName()))
            .collect(Collectors.toList());
  }

  @Override
  public Company createCompany(CompanyRequest companyRequest) {
    Company company = new Company();
    company.setName(companyRequest.getName());
    company = companyRepository.save(company);
    return company;
  }

  @Override
  public Company updateCompany(CompanyRequest companyRequest) {
    Company company = this.findById(companyRequest.getId());
    company.setName(companyRequest.getName());
    company = companyRepository.save(company);
    return company;
  }
}
