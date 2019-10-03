package com.dovit.backend.services;

import com.dovit.backend.domain.Company;
import com.dovit.backend.domain.Role;
import com.dovit.backend.domain.User;
import com.dovit.backend.exceptions.BadRequestException;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.requests.AuthRequest;
import com.dovit.backend.model.requests.RegisterTokenRequest;
import com.dovit.backend.model.requests.SignUpRequest;
import com.dovit.backend.model.responses.AuthResponse;
import com.dovit.backend.model.responses.RegisterTokenResponse;
import com.dovit.backend.repositories.RoleRepository;
import com.dovit.backend.repositories.UserRepository;
import com.dovit.backend.security.JwtTokenProvider;
import com.dovit.backend.security.UserPrincipal;
import com.dovit.backend.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 02-10-2019
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CompanyService companyService;

    @Override
    public AuthResponse authenticateUser(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String jwt = tokenProvider.generateAuthToken(authentication);
        String companyName = null;
        if (userPrincipal.getCompany() != null){
            companyName = userPrincipal.getCompany().getName();
        }
        return new AuthResponse(jwt, userPrincipal.getName(), userPrincipal.getLastName(), authentication.getAuthorities().stream().map(Object::toString).collect(Collectors.joining(", ")), companyName);
    }

    @Override
    @Transactional
    public User registerUser(SignUpRequest signUpRequest) {
        RegisterTokenRequest tokenInfo = this.getRegisterTokenInfo(signUpRequest.getRegistrationToken());
        Company company = companyService.findById(tokenInfo.getCompanyId());
        Role role = roleService.findById(Constants.ROLE_CLIENT_ID);

        User user = new User();
        user.setName(signUpRequest.getName());
        user.setLastName(signUpRequest.getLastName());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        user.setEmail(tokenInfo.getEmail());
        user.setCompany(company);
        user.setRole(role);


        user = userRepository.save(user);
        return user;
    }

    @Override
    public String createUserToken(RegisterTokenRequest registerTokenRequest) {
        return tokenProvider.generateRegisterToken(registerTokenRequest);
    }

    @Override
    public RegisterTokenRequest getRegisterTokenInfo(String token) {
        if (tokenProvider.validateToken(token)){
            return tokenProvider.getRegisterRequestFromJWT(token);
        }else {
            throw new BadRequestException("Token no válido");
        }

    }

}