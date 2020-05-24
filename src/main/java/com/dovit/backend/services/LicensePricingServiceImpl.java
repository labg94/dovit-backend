package com.dovit.backend.services;

import com.dovit.backend.domain.License;
import com.dovit.backend.domain.LicensePricing;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.requests.LicensePricingRequest;
import com.dovit.backend.model.responses.LicensePricingResponse;
import com.dovit.backend.repositories.LicensePricingRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ramón París
 * @since 23-05-20
 */
@Service
@RequiredArgsConstructor
public class LicensePricingServiceImpl implements LicensePricingService {

  private final LicensePricingRepository licensePricingRepository;
  private final ModelMapper modelMapper;

  @Override
  @Transactional
  public LicensePricing save(LicensePricingRequest licensePricingRequest, Long licenseId) {
    licensePricingRequest.setId(null);
    LicensePricing licensePricing = modelMapper.map(licensePricingRequest, LicensePricing.class);
    licensePricing.setLicense(License.builder().id(licenseId).build());
    return licensePricingRepository.save(licensePricing);
  }

  @Override
  @Transactional
  public LicensePricing update(LicensePricingRequest licensePricingRequest, Long licenseId) {
    LicensePricing licensePricing = findById(licensePricingRequest.getId());
    modelMapper.map(licensePricingRequest, licensePricing);
    licensePricing.getLicense().setId(licenseId);
    return licensePricingRepository.save(licensePricing);
  }

  private LicensePricing findById(Long id) {
    return licensePricingRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("License Pricing", "id", id));
  }

  @Override
  @Transactional
  public void delete(Long id) {
    licensePricingRepository.deleteById(id);
  }

  @Override
  @Transactional
  public LicensePricingResponse findResponseById(Long id) {
    return modelMapper.map(this.findById(id), LicensePricingResponse.class);
  }
}
