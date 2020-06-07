package com.dovit.backend.services;

import com.dovit.backend.domain.LicensePricing;
import com.dovit.backend.payloads.requests.LicensePricingRequest;
import com.dovit.backend.payloads.responses.LicensePricingResponse;

/**
 * @author Ramón París
 * @since 23-05-20
 */
public interface LicensePricingService {

  LicensePricing save(LicensePricingRequest licensePricingRequest, Long licenseId);

  LicensePricing update(LicensePricingRequest licensePricingRequest, Long licenseId);

  void delete(Long id);

  LicensePricingResponse findResponseById(Long id);
}
