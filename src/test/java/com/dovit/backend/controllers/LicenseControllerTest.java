package com.dovit.backend.controllers;

import com.dovit.backend.domain.License;
import com.dovit.backend.model.requests.LicenseRequest;
import com.dovit.backend.services.LicenseServiceImpl;
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

import static com.dovit.backend.util.RequestBuilderUtil.getLicenseRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

/**
 * @author Ramón París
 * @since 30-03-20
 */
@ExtendWith(SpringExtension.class)
class LicenseControllerTest {

  @InjectMocks LicenseController licenseController;
  @Mock LicenseServiceImpl licenseService;
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(licenseController).build();
  }

  @Test
  void findAllLicensesOfTool() {
    Mockito.when(licenseService.findAllByToolId(anyLong())).thenReturn(new ArrayList<>());
    TestUtils.testGetRequest(mockMvc, "/tool/1/licenses");
  }

  @Test
  void save() {
    Mockito.when(licenseService.save(any(LicenseRequest.class))).thenReturn(new License());
    TestUtils.testPostRequest(mockMvc, "/license", getLicenseRequest());
  }

  @Test
  void update() {
    Mockito.when(licenseService.update(any(LicenseRequest.class))).thenReturn(new License());
    TestUtils.testPutRequest(mockMvc, "/license", getLicenseRequest());
  }
}
