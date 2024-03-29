package com.dovit.backend.services;

import com.dovit.backend.payloads.responses.ProfileResponse;

import java.util.List;

/**
 * @author Ramón París
 * @since 10-12-2019
 */
public interface ProfileService {

  List<ProfileResponse> findAll();
}
