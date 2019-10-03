package com.dovit.backend.services;

import com.dovit.backend.domain.User;
import com.dovit.backend.model.requests.RegisterTokenRequest;
import com.dovit.backend.model.requests.UserRequest;
import com.dovit.backend.model.responses.UserResponse;

import java.util.List;

/**
 * @author Ramón París
 * @since 02-10-2019
 */
public interface UserService {

    List<UserResponse> findAllAdmins();

    User createUser(UserRequest userRequest);

    User updateUser(UserRequest request);

    User findById(Long id);

    List<UserResponse> findAllClients(Long companyId);

    String createUserToken(RegisterTokenRequest registerTokenRequest);
}
