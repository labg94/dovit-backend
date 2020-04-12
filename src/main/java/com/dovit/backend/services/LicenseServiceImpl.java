package com.dovit.backend.services;

import com.dovit.backend.domain.License;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.responses.LicenseResponse;
import com.dovit.backend.repositories.LicenseRepository;
import lombok.RequiredArgsConstructor;
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
  public License findById(Long licenseId) {
    return licenseRepository
        .findById(licenseId)
        .orElseThrow(() -> new ResourceNotFoundException("License", "id", licenseId));
  }
}
