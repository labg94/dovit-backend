package com.dovit.backend.controllers;

import com.dovit.backend.domain.CompanyLicense;
import com.dovit.backend.payloads.requests.CompanyLicenseRequest;
import com.dovit.backend.services.CompanyLicenseServiceImpl;
import com.dovit.backend.util.DomainBuilderUtil;
import com.dovit.backend.util.RequestBuilderUtil;
import com.dovit.backend.util.TestUtils;
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

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Ramón París
 * @since 30-03-20
 */
@ExtendWith(SpringExtension.class)
class CompanyLicenseControllerTest {

  @InjectMocks CompanyLicenseController companyLicenseController;
  @Mock CompanyLicenseServiceImpl companyService;
  private MockMvc mockMvc;

  private CompanyLicenseRequest companyLicenseRequest = RequestBuilderUtil.companyLicenseRequest;

  private CompanyLicense companyLicense = DomainBuilderUtil.companyLicense;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(companyLicenseController).build();
  }

  @Test
  void findAllLicenses() throws Exception {
    Mockito.when(companyService.findAllByCompanyIdAndToolId(anyLong(), anyLong()))
        .thenReturn(new ArrayList<>());
    MvcResult mvcResult =
        mockMvc
            .perform(MockMvcRequestBuilders.get("http://localhost:8080/api/company/1/tool/1/"))
            .andExpect(status().isOk())
            .andReturn();

    assertNotNull(mvcResult.getResponse().getContentAsString());
  }

  @Test
  void createCompanyLicense() {
    Mockito.when(companyService.createCompanyLicense(any(CompanyLicenseRequest.class)))
        .thenReturn(companyLicense);
    TestUtils.testPostRequest(mockMvc, "/company/license", companyLicenseRequest);
  }

  @Test
  void updateCompanyLicense() {
    Mockito.when(companyService.updateCompanyLicense(any(CompanyLicenseRequest.class)))
        .thenReturn(companyLicense);
    TestUtils.testPutRequest(mockMvc, "/company/license", companyLicenseRequest);
  }

  @Test
  void deleteCompanyLicense() {
    Mockito.when(companyService.deleteCompanyLicense(anyLong())).thenReturn(true);
    TestUtils.testDeleteRequest(mockMvc, "/company/license/1");
  }
}
