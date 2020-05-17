package com.dovit.backend.controllers;

import com.dovit.backend.model.responses.PagedResponse;
import com.dovit.backend.services.AuditServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

  @Test
  void findAllByDates() throws Exception {
    Mockito.when(
            auditService.findAllBetweenDates(
                any(LocalDateTime.class), any(LocalDateTime.class), anyInt(), anyInt()))
        .thenReturn(new PagedResponse<>());

    MvcResult mvcResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("http://localhost:8080/api/audits")
                    .param("page", "1")
                    .param("size", "25")
                    .param("fromDate", "2020-01-01T18:00")
                    .param("toDate", "2020-05-16T00:00"))
            .andExpect(status().isOk())
            .andReturn();

    assertNotNull(mvcResult.getResponse().getContentAsString());
  }
}
