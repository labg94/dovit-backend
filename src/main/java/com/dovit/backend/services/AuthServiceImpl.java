package com.dovit.backend.services;

import com.dovit.backend.domain.Company;
import com.dovit.backend.domain.Role;
import com.dovit.backend.domain.User;
import com.dovit.backend.exceptions.BadRequestException;
import com.dovit.backend.exceptions.CustomBadCredentialsException;
import com.dovit.backend.payloads.requests.AuthRequest;
import com.dovit.backend.payloads.requests.AzureAuthRequest;
import com.dovit.backend.payloads.requests.RegisterTokenRequest;
import com.dovit.backend.payloads.requests.SignUpRequest;
import com.dovit.backend.payloads.responses.AuthResponse;
import com.dovit.backend.repositories.RoleRepository;
import com.dovit.backend.repositories.UserRepository;
import com.dovit.backend.security.CustomAzureUserDetails;
import com.dovit.backend.security.JwtTokenProvider;
import com.dovit.backend.security.UserPrincipal;
import com.dovit.backend.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 02-10-2019
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final RoleService roleService;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider tokenProvider;
  private final CompanyService companyService;
  private final AuditService auditService;
  private final RoleRepository roleRepository;
  private final CustomAzureUserDetails customAzureUserDetails;

  @Override
  public AuthResponse authenticateUser(AuthRequest request) {
    Authentication authentication;
    try {
      authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    } catch (BadCredentialsException badCredentialsException) {
      throw new CustomBadCredentialsException("User or password are wrong", request);
    }

    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    String jwt = tokenProvider.generateAuthToken(authentication);

    AuthResponse response =
        AuthResponse.builder()
            .accessToken(jwt)
            .tokenType("Bearer")
            .name(userPrincipal.getName())
            .lastName(userPrincipal.getLastName())
            .role(
                authentication.getAuthorities().stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", ")))
            .company(userPrincipal.getCompanyName())
            .companyId(userPrincipal.getCompanyId())
            .userId(userPrincipal.getId())
            .build();

    auditService.registerAudit(response, "Login", "OK", userPrincipal.getId());
    return response;
  }

  @Override
  public AuthResponse authenticateByAzure(AzureAuthRequest azureAuthRequest) {
    final UserDetails userDetails =
        customAzureUserDetails.createUserDetailByAzure(azureAuthRequest.getTokenId());

    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);

    final UserPrincipal userPrincipal = (UserPrincipal) userDetails;
    String rolesString =
        authentication.getAuthorities().stream()
            .map(Object::toString)
            .collect(Collectors.joining(", "));

    AuthResponse response =
        new AuthResponse(
            azureAuthRequest.getTokenId(),
            userPrincipal.getName(),
            userPrincipal.getLastName(),
            rolesString,
            Constants.CLEVER_IT,
            0L);
    auditService.registerAudit(response, "Login", "OK", null);
    return response;
  }

  @Override
  @Transactional
  public User registerUser(SignUpRequest signUpRequest) {
    RegisterTokenRequest tokenInfo =
        this.getRegisterTokenInfo(signUpRequest.getRegistrationToken());
    Company company = companyService.findById(tokenInfo.getCompanyId());
    Role role = roleService.findById(tokenInfo.getRoleId());

    User user = new User();
    user.setName(signUpRequest.getName());
    user.setLastName(signUpRequest.getLastName());
    user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
    user.setRut(signUpRequest.getRut());
    user.setEmail(tokenInfo.getEmail());
    user.setCompany(company);
    user.setRole(role);
    user.setActive(true);

    user = userRepository.save(user);
    return user;
  }

  @Override
  public RegisterTokenRequest getRegisterTokenInfo(String token) {
    if (tokenProvider.validateToken(token)) {
      return tokenProvider.getRegisterRequestFromJWT(token);
    } else {
      throw new BadRequestException("Token no válido");
    }
  }
}
