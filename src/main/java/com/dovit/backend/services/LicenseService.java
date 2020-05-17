package com.dovit.backend.services;

import com.dovit.backend.model.responses.LicenseResponse;

import java.util.List;

/**
 * @author Ramón París
 * @since 01-10-2019
 */
public interface LicenseService {

  List<LicenseResponse> findAllByToolId(Long toolId);
}
