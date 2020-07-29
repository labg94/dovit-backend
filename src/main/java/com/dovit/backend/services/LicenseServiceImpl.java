package com.dovit.backend.services;

import com.dovit.backend.domain.License;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.payloads.requests.LicenseRequest;
import com.dovit.backend.payloads.responses.LicenseResponse;
import com.dovit.backend.repositories.LicensePricingRepository;
import com.dovit.backend.repositories.LicenseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 01-10-2019
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LicenseServiceImpl implements LicenseService {

  private final LicenseRepository licenseRepository;
  private final LicensePricingRepository licensePricingRepository;
  private final ModelMapper modelMapper;

  @Override
  @Transactional
  public List<LicenseResponse> findAllByToolId(Long toolId) {
    List<License> licenses = licenseRepository.findAllByToolId(toolId);
    return licenses.stream()
        .map(l -> modelMapper.map(l, LicenseResponse.class))
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public License save(LicenseRequest licenseRequest) {
    licenseRequest.setLicenseId(null);
    if (licenseRequest.getLicensePrices() != null) {
      licenseRequest.getLicensePrices().forEach(pricing -> pricing.setId(null));
    }

    if (licenseRequest.getActive() == null) {
      licenseRequest.setActive(true);
    }
    final License license = modelMapper.map(licenseRequest, License.class);
    license.getLicensePrices().forEach(licensePricing -> licensePricing.setLicense(license));
    return licenseRepository.save(license);
  }

  @Override
  @Transactional
  public License update(LicenseRequest licenseRequest) {
    final boolean exists = licenseRepository.existsById(licenseRequest.getLicenseId());
    if (exists) {
      final License license = modelMapper.map(licenseRequest, License.class);
      license.getLicensePrices().forEach(licensePricing -> licensePricing.setLicense(license));
      licensePricingRepository.deleteAllByLicenseId(license.getId());
      return licenseRepository.save(license);
    }

    return null;
  }

  @Override
  public void toggleActive(Long id) {
    License license = this.findLicenseById(id);
    license.setActive(!license.isActive());
    licenseRepository.save(license);
    log.info(
        "License with id {} toggled active from {} to {}",
        id,
        !license.isActive(),
        license.isActive());
  }

  @Override
  @Transactional
  public List<LicenseResponse> findAllActivesByToolId(Long toolId) {
    List<License> licenses = licenseRepository.findAllByToolIdAndActive(toolId, true);
    return licenses.stream()
        .map(l -> modelMapper.map(l, LicenseResponse.class))
        .collect(Collectors.toList());
  }

  private License findLicenseById(Long id) {
    return licenseRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("License", "id", id));
  }
}
