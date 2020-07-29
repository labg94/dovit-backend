package com.dovit.backend.controllers;

import com.dovit.backend.services.AuditServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @author Ramón París
 * @since 16-05-20
 */
@ExtendWith(SpringExtension.class)
class AuditControllerTest {

  private MockMvc mockMvc;
  @InjectMocks private AuditController auditController;
  @Mock private AuditServiceImpl auditService;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(auditController).build();
  }
}
