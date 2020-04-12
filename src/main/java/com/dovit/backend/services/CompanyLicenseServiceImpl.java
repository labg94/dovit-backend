package com.dovit.backend.services;

import com.dovit.backend.domain.CompanyLicense;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.requests.CompanyLicenseRequest;
import com.dovit.backend.model.responses.CompanyLicensesResponse;
import com.dovit.backend.repositories.CompanyLicenseRepository;
import com.dovit.backend.util.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 14-10-2019
 */
@Service
@RequiredArgsConstructor
public class CompanyLicenseServiceImpl implements CompanyLicenseService {

  private final CompanyLicenseRepository companyLicenseRepository;
  private final CompanyService companyService;
  private final LicenseService licenseService;
  private final ValidatorUtil validatorUtil;
  private final ModelMapper modelMapper;

  @Override
  @Transactional
  public List<CompanyLicensesResponse> findAllByCompanyIdAndToolId(Long companyId, Long toolId) {
    validatorUtil.canActOnCompany(companyId);
    List<CompanyLicense> companyLicenses =
        companyLicenseRepository.findAllByCompanyIdAndLicenseToolId(companyId, toolId);

    return companyLicenses.stream()
        .map(c -> modelMapper.map(c, CompanyLicensesResponse.class))
        .collect(Collectors.toList());
  }

  @Override
  public CompanyLicense createCompanyLicense(CompanyLicenseRequest request) {
    validatorUtil.canActOnCompany(request.getCompanyId());
    CompanyLicense companyLicense = modelMapper.map(request, CompanyLicense.class);
    companyLicense = companyLicenseRepository.save(companyLicense);
    return companyLicense;
  }

  @Override
  public CompanyLicense updateCompanyLicense(CompanyLicenseRequest request) {
    validatorUtil.canActOnCompany(request.getCompanyId());
    CompanyLicense companyLicense =
        companyLicenseRepository
            .findById(request.getId())
            .orElseThrow(
                () -> new ResourceNotFoundException("CompanyLicense", "id", request.getId()));
    //    companyLicense.setStartDate(request.getStartDate().toInstant());
    if (request.getExpirationDate() != null) {
      //      companyLicense.setExpirationDate(request.getExpirationDate().toInstant());
    } else {
      companyLicense.setExpirationDate(null);
    }
    companyLicense = companyLicenseRepository.save(companyLicense);
    return companyLicense;
  }

  @Override
  public boolean deleteCompanyLicense(Long companyLicenseId) {
    CompanyLicense companyLicense =
        companyLicenseRepository
            .findById(companyLicenseId)
            .orElseThrow(
                () -> new ResourceNotFoundException("CompanyLicense", "id", companyLicenseId));
    validatorUtil.canActOnCompany(companyLicense.getCompany().getId());
    companyLicenseRepository.delete(companyLicense);
    return true;
  }
}
