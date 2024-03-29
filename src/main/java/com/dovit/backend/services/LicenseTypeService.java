package com.dovit.backend.services;

import com.dovit.backend.payloads.responses.MasterRegistryResponse;

import java.util.List;

/**
 * @author Ramón París
 * @since 23-05-20
 */
public interface LicenseTypeService {

  List<MasterRegistryResponse> findAll();
}
