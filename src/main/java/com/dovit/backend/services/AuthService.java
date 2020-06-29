package com.dovit.backend.services;

import com.dovit.backend.domain.User;
import com.dovit.backend.payloads.requests.AuthRequest;
import com.dovit.backend.payloads.requests.AzureAuthRequest;
import com.dovit.backend.payloads.requests.RegisterTokenRequest;
import com.dovit.backend.payloads.requests.SignUpRequest;
import com.dovit.backend.payloads.responses.AuthResponse;

/**
 * @author Ramón París
 * @since 02-10-2019
 */
public interface AuthService {

  AuthResponse authenticateUser(AuthRequest authRequest);

  AuthResponse authenticateByAzure(AzureAuthRequest azureAuthRequest);

  User registerUser(SignUpRequest signUpRequest);

  RegisterTokenRequest getRegisterTokenInfo(String token);
}
