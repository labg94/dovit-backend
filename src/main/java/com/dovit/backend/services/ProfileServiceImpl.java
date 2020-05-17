package com.dovit.backend.services;

import com.dovit.backend.model.responses.ProfileResponse;
import com.dovit.backend.repositories.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 10-12-2019
 */
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

  private final ProfileRepository profileRepository;
  private final ModelMapper modelMapper;

  @Override
  public List<ProfileResponse> findAll() {
    return profileRepository.findAll().stream()
        .map((p) -> modelMapper.map(p, ProfileResponse.class))
        .collect(Collectors.toList());
  }
}
