package com.dovit.backend.services;

import com.dovit.backend.domain.User;
import com.dovit.backend.model.requests.RegisterTokenRequest;
import com.dovit.backend.model.requests.UserRequest;
import com.dovit.backend.model.responses.UserResponse;
import com.dovit.backend.repositories.UserRepository;
import com.dovit.backend.security.JwtTokenProvider;
import com.dovit.backend.util.ValidatorUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;

/**
 * @author Ramón París
 * @since 29-03-20
 */
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

  @InjectMocks UserServiceImpl userService;
  @Mock UserRepository userRepository;
  @Mock PasswordEncoder passwordEncoder;
  @Mock JwtTokenProvider jwtTokenProvider;
  @Mock EmailServiceImpl emailService;
  @Mock ModelMapper modelMapper;
  @Mock ValidatorUtil validatorUtil;

  private Page<User> pages;
  private UserRequest admin = UserRequest.builder().id(1L).roleId(1L).password("12345678").build();
  private UserRequest client = UserRequest.builder().id(1L).companyId(1L).roleId(2L).build();

  @AfterEach
  void tearDown(TestInfo info) {
    if (!info.getTags().contains("SkipAfter")) modelMapper.validate();
  }

  @Test
  void findAllAdmins() {
    List<User> usersList = new ArrayList<>();
    usersList.add(Mockito.mock(User.class));
    this.pages = new PageImpl<>(usersList);
    Mockito.when(userRepository.findAllByRoleId(anyLong(), any(Pageable.class))).thenReturn(pages);
    assertNotNull(userService.findAllAdmins(1, 25));
  }

  @Test
  void findAllClients() {
    List<User> usersList = new ArrayList<>();
    usersList.add(Mockito.mock(User.class));
    this.pages = new PageImpl<>(usersList);
    Mockito.when(userRepository.findAllByCompanyId(anyLong(), any())).thenReturn(pages);
    assertNotNull(userService.findAllClients(1L, 1, 25));
  }

  @Test
  void createUser() {
    Mockito.when(userRepository.save(any(User.class))).thenReturn(new User());
    Mockito.when(modelMapper.map(any(UserRequest.class), any()))
        .thenReturn(Mockito.mock(User.class));
    Mockito.when(passwordEncoder.encode(anyString())).thenReturn("123456789");
    assertNotNull(userService.createUser(this.admin));
  }

  @Test
  void updateUser() {
    Mockito.when(userRepository.findById(anyLong()))
        .thenReturn(Optional.of(Mockito.mock(User.class)));

    Mockito.when(userRepository.save(any(User.class))).thenReturn(new User());

    Mockito.when(modelMapper.map(any(UserRequest.class), any()))
        .thenReturn(Mockito.mock(User.class));

    Mockito.when(passwordEncoder.encode(anyString())).thenReturn("123456789");

    assertNotNull(userService.updateUser(this.admin));
    assertNotNull(userService.updateUser(this.client));
  }

  @Test
  void createUserToken() {
    RegisterTokenRequest registerTokenRequest =
        RegisterTokenRequest.builder().companyId(1L).email("").build();

    Mockito.when(userRepository.existsByEmail(anyString())).thenReturn(true);
    assertThrows(
        DataIntegrityViolationException.class,
        () -> userService.createUserToken(registerTokenRequest));

    Mockito.when(userRepository.existsByEmail(anyString())).thenReturn(false);
    Mockito.when(jwtTokenProvider.generateRegisterToken(any(RegisterTokenRequest.class)))
        .thenReturn("token");
    Mockito.doNothing().when(emailService).sendSimpleMessage(anyString(), anyString(), anyString());
    String response = userService.createUserToken(registerTokenRequest);
    assertNotNull(response);
    assertEquals(response, "token");
  }

  @Test
  void findResponseById() {
    Mockito.when(userRepository.findById(anyLong()))
        .thenReturn(Optional.of(Mockito.mock(User.class)));
    UserResponse userResponse = new UserResponse();
    Mockito.when(modelMapper.map(any(User.class), any())).thenReturn(userResponse);
    UserResponse response = userService.findResponseById(1L);
    assertNotNull(response);
    assertEquals(userResponse, response);
  }

  @Test
  void findById() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method findById = UserServiceImpl.class.getDeclaredMethod("findById", Long.class);
    findById.setAccessible(true);

    Mockito.when(userRepository.findById(anyLong()))
        .thenReturn(Optional.of(Mockito.mock(User.class)));
    assertNotNull(findById.invoke(userService, 1L));

    Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(Exception.class, () -> findById.invoke(userService, 1L));
  }

  @Test
  void checkValidRequest()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method checkValidRequest =
        UserServiceImpl.class.getDeclaredMethod("checkValidRequest", UserRequest.class);
    checkValidRequest.setAccessible(true);

    checkValidRequest.invoke(userService, admin);
    checkValidRequest.invoke(userService, client);

    UserRequest wrongAdmin = UserRequest.builder().roleId(1L).companyId(1L).build();
    assertThrows(Exception.class, () -> checkValidRequest.invoke(userService, wrongAdmin));

    UserRequest wrongClient = UserRequest.builder().roleId(2L).build();
    assertThrows(Exception.class, () -> checkValidRequest.invoke(userService, wrongClient));
  }
}
