package com.dovit.backend.services;

import com.dovit.backend.payloads.responses.MasterRegistryResponse;
import com.dovit.backend.repositories.LicenseTypeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 23-05-20
 */
@RequiredArgsConstructor
@Service
public class LicenseTypeServiceImpl implements LicenseTypeService {

  private final LicenseTypeRepository repository;
  private final ModelMapper modelMapper;

  @Override
  public List<MasterRegistryResponse> findAll() {
    return repository.findAll().stream()
        .map(l -> modelMapper.map(l, MasterRegistryResponse.class))
        .collect(Collectors.toList());
  }
}
