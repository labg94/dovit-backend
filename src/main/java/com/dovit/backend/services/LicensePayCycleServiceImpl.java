package com.dovit.backend.services;

import com.dovit.backend.model.responses.MasterRegistryResponse;
import com.dovit.backend.repositories.LicensePayCycleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 23-05-20
 */
@Service
@RequiredArgsConstructor
public class LicensePayCycleServiceImpl implements LicensePayCycleService {

  private final LicensePayCycleRepository repository;
  private final ModelMapper modelMapper;

  @Override
  public List<MasterRegistryResponse> findAll() {
    return repository.findAll().stream()
        .map(licensePayCycle -> modelMapper.map(licensePayCycle, MasterRegistryResponse.class))
        .collect(Collectors.toList());
  }
}
