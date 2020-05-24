package com.dovit.backend.services;

import com.dovit.backend.domain.License;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.requests.LicenseRequest;
import com.dovit.backend.model.responses.LicenseResponse;
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
    licenseRequest.getLicensePrices().forEach(pricing -> pricing.setId(null));
    final License license = modelMapper.map(licenseRequest, License.class);
    license.getLicensePrices().forEach(licensePricing -> licensePricing.setLicense(license));
    return licenseRepository.save(license);
  }

  @Override
  @Transactional
  public License update(LicenseRequest licenseRequest) {
    License license = findLicenseById(licenseRequest.getLicenseId());
    licenseRequest.setToolId(null);
    licenseRequest.setLicensePrices(null);
    modelMapper.map(licenseRequest, license);

    // TODO por algún motivo el map me está cambiando el ID de la licencia por el valor en el
    // licensePricingId.. En honor al tiempo se hizo esta weá, pero se tiene que mejorar
    license.setId(licenseRequest.getLicenseId());
    return licenseRepository.save(license);
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

  private License findLicenseById(Long id) {
    return licenseRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("License", "id", id));
  }
}
