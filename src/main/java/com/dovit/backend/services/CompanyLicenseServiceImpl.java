package com.dovit.backend.services;

import com.dovit.backend.domain.CompanyLicense;
import com.dovit.backend.domain.License;
import com.dovit.backend.exceptions.BadRequestException;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.payloads.requests.CompanyLicenseRequest;
import com.dovit.backend.payloads.responses.CompanyLicensesResponse;
import com.dovit.backend.repositories.CompanyLicenseRepository;
import com.dovit.backend.util.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.dovit.backend.util.DateUtil.isBetween;

/**
 * @author Ramón París
 * @since 14-10-2019
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyLicenseServiceImpl implements CompanyLicenseService {

  private final CompanyLicenseRepository companyLicenseRepository;
  private final ValidatorUtil validatorUtil;
  private final ModelMapper modelMapper;

  @Override
  @Transactional
  public List<CompanyLicensesResponse> findAllByCompanyId(Long companyId) {
    validatorUtil.canActOnCompany(companyId);
    List<CompanyLicense> companyLicenses =
        companyLicenseRepository.findAllByCompanyIdOrderByStartDateDesc(companyId);

    final LocalDate now = LocalDate.now();
    return companyLicenses.stream()
        .map(c -> mapCompanyLicenseResponse(companyId, now, c))
        .collect(Collectors.toList());
  }

  private CompanyLicensesResponse mapCompanyLicenseResponse(
      Long companyId, LocalDate now, CompanyLicense c) {
    final CompanyLicensesResponse response = modelMapper.map(c, CompanyLicensesResponse.class);

    response.setActive(isBetween(now, c.getStartDate(), c.getExpirationDate()));

    final Long membersUsingQty =
        c.getLicense().getTool().getToolProfile().stream()
            .filter(toolProfile -> toolProfile.getMember().getCompany().getId().equals(companyId))
            .count();
    response.setMembersUsingQty(membersUsingQty);

    c.getLicense().getLicensePrices().stream()
        .filter(licensePricing -> licensePricing.getPrice() != null)
        .filter(licensePricing -> licensePricing.getPrice().compareTo(-1.0) > 0)
        .filter(
            licensePricing ->
                membersUsingQty.compareTo(licensePricing.getMinUsers()) >= 0
                    && (licensePricing.getMaxUsers().compareTo(-1L) == 0
                        || membersUsingQty.compareTo(licensePricing.getMaxUsers()) <= 0))
        .filter(licensePricing -> licensePricing.getPrice() != null)
        .findFirst()
        .ifPresent(
            licensePricing -> response.setPricing(licensePricing.getPrice() * membersUsingQty));

    return response;
  }

  @Override
  @Transactional
  public CompanyLicense createCompanyLicense(CompanyLicenseRequest request) {
    validatorUtil.canActOnCompany(request.getCompanyId());
    checkCurrentLicense(request);
    CompanyLicense companyLicense = modelMapper.map(request, CompanyLicense.class);
    companyLicense = companyLicenseRepository.save(companyLicense);
    log.info(
        "License {} associated to company {}. ID: {}",
        request.getLicenseId(),
        request.getCompanyId(),
        companyLicense.getId());
    return companyLicense;
  }

  @Override
  @Transactional
  public CompanyLicense updateCompanyLicense(CompanyLicenseRequest request) {
    validatorUtil.canActOnCompany(request.getCompanyId());
    CompanyLicense companyLicense =
        companyLicenseRepository
            .findById(request.getId())
            .orElseThrow(
                () -> new ResourceNotFoundException("CompanyLicense", "id", request.getId()));

    checkCurrentLicense(request);
    companyLicense.setStartDate(request.getStartDate());
    companyLicense.setExpirationDate(request.getExpirationDate());
    companyLicense.setLicense(License.builder().id(request.getLicenseId()).build());
    companyLicense = companyLicenseRepository.save(companyLicense);
    log.info("CompanyLicense {} updated", companyLicense.getId());
    return companyLicense;
  }

  @Override
  @Transactional
  public boolean deleteCompanyLicense(Long companyLicenseId) {
    CompanyLicense companyLicense =
        companyLicenseRepository
            .findById(companyLicenseId)
            .orElseThrow(
                () -> new ResourceNotFoundException("CompanyLicense", "id", companyLicenseId));
    validatorUtil.canActOnCompany(companyLicense.getCompany().getId());
    companyLicenseRepository.delete(companyLicense);
    log.info("CompanyLicense {} deleted", companyLicense.getId());
    return true;
  }

  @Override
  public CompanyLicensesResponse findAllByCompanyIdAndToolId(Long companyId, Long toolId) {
    final Optional<CompanyLicense> companyLicense =
        companyLicenseRepository.findByCompanyIdAndLicenseToolIdOrderByStartDateDesc(
            companyId, toolId);
    LocalDate now = LocalDate.now();
    return companyLicense.map(c -> this.mapCompanyLicenseResponse(companyId, now, c)).orElseThrow();
  }

  private void checkCurrentLicense(CompanyLicenseRequest request) {
    List<CompanyLicense> companyLicenses =
        companyLicenseRepository.findAllLicensesByCompanyIdAndLicenseId(
            request.getCompanyId(), request.getLicenseId());

    boolean notValidDate =
        companyLicenses.stream()
            .filter(companyLicense -> !companyLicense.getId().equals(request.getId()))
            .anyMatch(
                companyLicense ->
                    isBetween(
                            request.getStartDate(),
                            companyLicense.getStartDate(),
                            companyLicense.getExpirationDate())
                        || isBetween(
                            request.getExpirationDate(),
                            companyLicense.getStartDate(),
                            companyLicense.getExpirationDate()));

    if (notValidDate) {
      throw new BadRequestException(
          String.format(
              "There is a conflict with license date from %s until %s",
              request.getStartDate(),
              request.getExpirationDate() != null ? request.getExpirationDate() : "Undefined"));
    }
  }
}
