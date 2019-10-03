package com.dovit.backend.services;

import com.dovit.backend.domain.User;
import com.dovit.backend.model.requests.UserRequest;

/**
 * @author Ramón París
 * @since 02-10-2019
 */
public interface UserService {

    User createUser(UserRequest userRequest);

    User updateUser(UserRequest request);

    User findById(Long id);
}
