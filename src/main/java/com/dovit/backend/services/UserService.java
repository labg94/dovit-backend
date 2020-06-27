package com.dovit.backend.services;

import com.dovit.backend.domain.User;
import com.dovit.backend.payloads.requests.RegisterTokenRequest;
import com.dovit.backend.payloads.requests.UserRequest;
import com.dovit.backend.payloads.responses.PagedResponse;
import com.dovit.backend.payloads.responses.UserResponse;

/**
 * @author Ramón París
 * @since 02-10-2019
 */
public interface UserService {

  PagedResponse<UserResponse> findAllAdmins(int page, int size);

  PagedResponse<UserResponse> findAllClients(Long companyId, int page, int size);

  User createUser(UserRequest userRequest);

  User updateUser(UserRequest request);

  UserResponse findResponseById(Long userId);

  String createUserToken(RegisterTokenRequest registerTokenRequest);

  void toggleActive(Long userId);
}
