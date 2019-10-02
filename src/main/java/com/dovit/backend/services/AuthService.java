package com.dovit.backend.services;

import com.dovit.backend.domain.User;
import com.dovit.backend.model.requests.AuthRequest;
import com.dovit.backend.model.requests.SignUpRequest;
import com.dovit.backend.model.responses.AuthResponse;

/**
 * @author Ramón París
 * @since 02-10-2019
 */
public interface AuthService {

    AuthResponse authenticateUser(AuthRequest authRequest);


    User registerUser(SignUpRequest signUpRequest);
}
