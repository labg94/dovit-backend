package com.dovit.backend.services;

import com.dovit.backend.domain.User;
import com.dovit.backend.exceptions.BadRequestException;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.payloads.requests.RegisterTokenRequest;
import com.dovit.backend.payloads.requests.UserRequest;
import com.dovit.backend.payloads.responses.PagedResponse;
import com.dovit.backend.payloads.responses.UserResponse;
import com.dovit.backend.repositories.UserRepository;
import com.dovit.backend.security.JwtTokenProvider;
import com.dovit.backend.util.Constants;
import com.dovit.backend.util.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 02-10-2019
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider tokenProvider;
  private final EmailService emailService;
  private final ModelMapper modelMapper;
  private final ValidatorUtil validatorUtil;

  @Value("${app.frontend.domain}")
  private String APP_FRONTEND_DOMAIN;

  @Override
  public PagedResponse<UserResponse> findAllAdmins(int page, int size) {
    Pageable pagination = PageRequest.of(page, size);
    Page<User> usersPage = userRepository.findAllByRoleId(Constants.ROLE_ADMIN_ID, pagination);
    List<UserResponse> users =
        usersPage.getContent().stream()
            .map(u -> modelMapper.map(u, UserResponse.class))
            .collect(Collectors.toList());

    return new PagedResponse<>(
        users,
        usersPage.getNumber(),
        usersPage.getSize(),
        usersPage.getTotalElements(),
        usersPage.getTotalPages(),
        usersPage.isLast());
  }

  @Override
  public PagedResponse<UserResponse> findAllClients(Long companyId, int page, int size) {
    validatorUtil.canActOnCompany(companyId);
    Pageable pagination = PageRequest.of(page, size);
    Page<User> usersPage = userRepository.findAllByCompanyId(companyId, pagination);
    List<UserResponse> users =
        usersPage.getContent().stream()
            .map(u -> modelMapper.map(u, UserResponse.class))
            .collect(Collectors.toList());

    return new PagedResponse<>(
        users,
        usersPage.getNumber(),
        usersPage.getSize(),
        usersPage.getTotalElements(),
        usersPage.getTotalPages(),
        usersPage.isLast());
  }

  @Override
  public User createUser(UserRequest userRequest) {
    checkValidRequest(userRequest);
    User user = modelMapper.map(userRequest, User.class);
    user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
    return userRepository.save(user);
  }

  @Override
  public User updateUser(UserRequest userRequest) {
    checkValidRequest(userRequest);
    User user = this.findById(userRequest.getId());
    modelMapper.map(userRequest, user);
    if (userRequest.getPassword() != null) {
      user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
    }

    if (userRequest.getCompanyId() == null) {
      user.setCompany(null);
    }

    return userRepository.save(user);
  }

  @Override
  public String createUserToken(RegisterTokenRequest registerTokenRequest) {
    final Boolean exists = userRepository.existsByEmail(registerTokenRequest.getEmail());
    if (exists) {
      throw new DataIntegrityViolationException(
          "Detail:Email " + registerTokenRequest.getEmail() + " ya se encuentra en base de datos");
    }

    String token = tokenProvider.generateRegisterToken(registerTokenRequest);
    emailService.sendRegistration(
        registerTokenRequest.getEmail(), APP_FRONTEND_DOMAIN + "/userCompany/create/" + token);
    return token;
  }

  @Override
  public void toggleActive(Long userId) {
    User user = this.findById(userId);
    user.setActive(!user.isActive());
    userRepository.save(user);
  }

  @Override
  public List<UserResponse> findAll() {
    List<User> users = userRepository.findAll();
    return users.stream()
        .map(u -> modelMapper.map(u, UserResponse.class))
        .collect(Collectors.toList());
  }

  @Override
  public List<UserResponse> findAllByCompanyId(Long companyId) {
    List<User> users = userRepository.findAllByCompanyId(companyId);
    return users.stream()
        .map(u -> modelMapper.map(u, UserResponse.class))
        .collect(Collectors.toList());
  }

  @Override
  public UserResponse findResponseById(Long userId) {
    User u = this.findById(userId);
    return modelMapper.map(u, UserResponse.class);
  }

  private User findById(Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
  }

  private void checkValidRequest(UserRequest userRequest) {
    if (userRequest.getRoleId().equals(Constants.ROLE_ADMIN_ID)
        && userRequest.getCompanyId() != null) {
      throw new BadRequestException("Administrator role should not have any company associated");
    }

    if (userRequest.getRoleId().equals(Constants.ROLE_CLIENT_ID)
        && userRequest.getCompanyId() == null) {
      throw new BadRequestException("Client role should have a company associated");
    }
  }
}
