package com.dovit.backend.controllers;

import com.dovit.backend.domain.User;
import com.dovit.backend.model.requests.RegisterTokenRequest;
import com.dovit.backend.model.requests.UserRequest;
import com.dovit.backend.model.responses.PagedResponse;
import com.dovit.backend.model.responses.UserResponse;
import com.dovit.backend.services.UserServiceImpl;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Ramón París
 * @since 30-03-20
 */
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  @InjectMocks UserController userController;
  @Mock UserServiceImpl userService;
  private MockMvc mockMvc;

  private User user =
      User.builder()
          .id(1L)
          .name("Ramón")
          .lastName("París")
          .email("ramon.paris@inacapmail.cl")
          .password("password")
          .rut("25412683-7")
          .active(true)
          .build();

  private UserRequest userRequest =
      UserRequest.builder()
          .id(1L)
          .roleId(1L)
          .password("12345678")
          .name("Ramón")
          .lastName("París")
          .email("ramon.paris@inacapmail.cl")
          .rut("25412683-7")
          .active(true)
          .roleId(1L)
          .companyId(null)
          .build();

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
  }

  @Test
  void findAllAdmin() throws Exception {
    Mockito.when(userService.findAllAdmins(anyInt(), anyInt())).thenReturn(new PagedResponse<>());
    MvcResult mvcResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("http://localhost:8080/api/users/admin")
                    .param("page", "1")
                    .param("size", "25"))
            .andExpect(status().isOk())
            .andReturn();

    assertNotNull(mvcResult.getResponse().getContentAsString());
  }

  @Test
  void findAllUsers() throws Exception {
    Mockito.when(userService.findAllClients(anyLong(), anyInt(), anyInt()))
        .thenReturn(new PagedResponse<>());
    MvcResult mvcResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("http://localhost:8080/api/company/1/users")
                    .param("page", "1")
                    .param("size", "25"))
            .andExpect(status().isOk())
            .andReturn();

    assertNotNull(mvcResult.getResponse().getContentAsString());
  }

  @Test
  void createRegisterToken() throws Exception {
    Mockito.when(userService.createUserToken(any(RegisterTokenRequest.class))).thenReturn("token");
    String request =
        new Gson()
            .toJson(
                RegisterTokenRequest.builder()
                    .email("ramon.paris@inacapmail.cl")
                    .companyId(1L)
                    .build());

    MvcResult mvcResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("http://localhost:8080/api/user/token/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request))
            .andExpect(status().isOk())
            .andReturn();

    assertNotNull(mvcResult.getResponse().getContentAsString());
  }

  @Test
  void findById() throws Exception {
    Mockito.when(userService.findResponseById(anyLong())).thenReturn(new UserResponse());

    MvcResult mvcResult =
        mockMvc
            .perform(MockMvcRequestBuilders.get("http://localhost:8080/api/user/1/"))
            .andExpect(status().isOk())
            .andReturn();

    assertNotNull(mvcResult.getResponse().getContentAsString());
  }

  @Test
  void createUser() throws Exception {
    Mockito.when(userService.createUser(any(UserRequest.class))).thenReturn(this.user);
    String request = new Gson().toJson(this.userRequest);
    MvcResult mvcResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("http://localhost:8080/api/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request))
            .andExpect(status().isCreated())
            .andReturn();

    assertNotNull(mvcResult.getResponse().getContentAsString());
  }

  @Test
  void updateUser() throws Exception {
    Mockito.when(userService.updateUser(any(UserRequest.class))).thenReturn(this.user);
    String request = new Gson().toJson(this.userRequest);
    MvcResult mvcResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.put("http://localhost:8080/api/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request))
            .andExpect(status().isCreated())
            .andReturn();

    assertNotNull(mvcResult.getResponse().getContentAsString());
  }
}
