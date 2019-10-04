package com.dovit.backend.services;

import com.dovit.backend.domain.User;
import com.dovit.backend.model.requests.RegisterTokenRequest;
import com.dovit.backend.model.requests.UserRequest;
import com.dovit.backend.model.responses.PagedResponse;
import com.dovit.backend.model.responses.UserResponse;

import java.util.List;

/**
 * @author Ramón París
 * @since 02-10-2019
 */
public interface UserService {

    PagedResponse<UserResponse> findAllAdmins(int page, int size);

    PagedResponse<UserResponse> findAllClients(Long companyId, int page, int size);

    User createUser(UserRequest userRequest);

    User updateUser(UserRequest request);

    User findById(Long id);


    String createUserToken(RegisterTokenRequest registerTokenRequest);
}
