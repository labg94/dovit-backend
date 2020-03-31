package com.dovit.backend.controllers;

import com.dovit.backend.services.LevelServiceImpl;
import com.dovit.backend.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

/**
 * @author Ramón París
 * @since 30-03-20
 */
@ExtendWith(SpringExtension.class)
class LevelControllerTest {

  @InjectMocks LevelController levelController;
  @Mock LevelServiceImpl levelService;
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(levelController).build();
  }

  @Test
  void findAll() {
    Mockito.when(levelService.findAll()).thenReturn(new ArrayList<>());
    TestUtils.testGetRequest(mockMvc, "/levels");
  }
}
