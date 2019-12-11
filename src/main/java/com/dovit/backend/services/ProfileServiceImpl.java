package com.dovit.backend.services;

import com.dovit.backend.model.responses.ProfileResponse;
import com.dovit.backend.repositories.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 10-12-2019
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public List<ProfileResponse> findAll() {
        return profileRepository.findAll().stream().map(ProfileResponse::new).collect(Collectors.toList());
    }
}
