package com.dovit.backend.controllers;

import com.dovit.backend.domain.User;
import com.dovit.backend.payloads.requests.AuthRequest;
import com.dovit.backend.payloads.requests.RegisterTokenRequest;
import com.dovit.backend.payloads.requests.SignUpRequest;
import com.dovit.backend.payloads.responses.AuthResponse;
import com.dovit.backend.services.AuthServiceImpl;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Ramón París
 * @since 30-03-20
 */
@ExtendWith(SpringExtension.class)
class AuthControllerTest {

  @InjectMocks AuthController authController;
  @Mock AuthServiceImpl authService;
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
  }

  @Test
  void signIn() throws Exception {
    Mockito.when(authService.authenticateUser(any(AuthRequest.class)))
        .thenReturn(new AuthResponse());

    AuthRequest authRequest =
        AuthRequest.builder().email("ramon.paris@inacapmail.cl").password("123456").build();

    String request = new Gson().toJson(authRequest);
    MvcResult mvcResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("http://localhost:8080/api/auth/signIn/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request))
            .andExpect(status().isOk())
            .andReturn();

    assertNotNull(mvcResult.getResponse().getContentAsString());
  }

  @Test
  void registerUser() throws Exception {
    Mockito.when(authService.registerUser(any(SignUpRequest.class)))
        .thenReturn(User.builder().id(1L).build());

    SignUpRequest signUpRequest =
        SignUpRequest.builder()
            .registrationToken("token")
            .name("Ramón")
            .lastName("París")
            .password("1234567")
            .rut("25412683-7")
            .build();

    String request = new Gson().toJson(signUpRequest);
    MvcResult mvcResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("http://localhost:8080/api/auth/signUp/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request))
            .andExpect(status().isCreated())
            .andReturn();

    assertNotNull(mvcResult.getResponse().getContentAsString());
  }

  @Test
  void getRegisterTokenInfo() throws Exception {
    Mockito.when(authService.getRegisterTokenInfo(anyString()))
        .thenReturn(new RegisterTokenRequest());

    MvcResult mvcResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("http://localhost:8080/api/auth/token/")
                    .param("token", "token"))
            .andExpect(status().isOk())
            .andReturn();

    assertNotNull(mvcResult.getResponse().getContentAsString());
  }
}
