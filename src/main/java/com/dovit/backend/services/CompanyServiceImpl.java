package com.dovit.backend.services;

import com.dovit.backend.domain.Company;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.payloads.requests.CompanyRequest;
import com.dovit.backend.payloads.responses.CompanyResponse;
import com.dovit.backend.repositories.CompanyRepository;
import com.dovit.backend.util.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 01-10-2019
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {

  private final CompanyRepository companyRepository;
  private final ValidatorUtil validatorUtil;
  private final ModelMapper modelMapper;

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
    return modelMapper.map(company, CompanyResponse.class);
  }

  @Override
  public List<CompanyResponse> findAll() {
    List<Company> companies = companyRepository.findAll();
    return companies.stream()
        .map(c -> modelMapper.map(c, CompanyResponse.class))
        .sorted(Comparator.comparing(CompanyResponse::getName))
        .collect(Collectors.toList());
  }

  @Override
  public Company createCompany(CompanyRequest companyRequest) {
    Company company = modelMapper.map(companyRequest, Company.class);
    company = companyRepository.save(company);
    log.info("Company {} created with id {}", company.getName(), company.getId());
    return company;
  }

  @Override
  public Company updateCompany(CompanyRequest companyRequest) {
    Company company = this.findById(companyRequest.getId());
    modelMapper.map(companyRequest, company);
    company = companyRepository.save(company);
    log.info("Company {} updated with id {}", company.getName(), company.getId());
    return company;
  }

  @Override
  @Transactional
  public void toggleCompanyStatus(Long id) {
    final Company company = findById(id);
    company.setActive(!company.isActive());
    companyRepository.save(company);
    log.info("Company {} toggle status", id);
  }

  @Override
  public List<CompanyResponse> findAllActives() {
    final List<Company> companies = companyRepository.findAllByActive(true);
    return companies.stream()
        .map(c -> modelMapper.map(c, CompanyResponse.class))
        .sorted(Comparator.comparing(CompanyResponse::getName))
        .collect(Collectors.toList());
  }
}
