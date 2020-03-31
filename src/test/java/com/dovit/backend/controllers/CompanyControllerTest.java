package com.dovit.backend.controllers;

import com.dovit.backend.domain.Company;
import com.dovit.backend.model.requests.CompanyRequest;
import com.dovit.backend.services.CompanyServiceImpl;
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
class CompanyControllerTest {

  @InjectMocks CompanyController companyController;
  @Mock CompanyServiceImpl companyService;
  private MockMvc mockMvc;

  private CompanyRequest companyRequest = CompanyRequest.builder().id(1L).name("Clever It").build();
  private Company company = Company.builder().id(1L).build();

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();
  }

  @Test
  void findAll() throws Exception {
    Mockito.when(companyService.findAll()).thenReturn(new ArrayList<>());
    MvcResult mvcResult =
        mockMvc
            .perform(MockMvcRequestBuilders.get("http://localhost:8080/api/companies/"))
            .andExpect(status().isOk())
            .andReturn();

    assertNotNull(mvcResult.getResponse().getContentAsString());
  }

  @Test
  void findById() throws Exception {
    Mockito.when(companyService.findById(anyLong())).thenReturn(Company.builder().id(1L).build());
    MvcResult mvcResult =
        mockMvc
            .perform(MockMvcRequestBuilders.get("http://localhost:8080/api/company/1"))
            .andExpect(status().isOk())
            .andReturn();

    assertNotNull(mvcResult.getResponse().getContentAsString());
  }

  @Test
  void createCompany() throws Exception {
    Mockito.when(companyService.createCompany(any(CompanyRequest.class))).thenReturn(this.company);
    String request = new Gson().toJson(this.companyRequest);
    MvcResult mvcResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("http://localhost:8080/api/company")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request))
            .andExpect(status().isCreated())
            .andReturn();

    assertNotNull(mvcResult.getResponse().getContentAsString());
  }

  @Test
  void updateCompany() throws Exception {
    Mockito.when(companyService.updateCompany(any(CompanyRequest.class))).thenReturn(this.company);
    String request = new Gson().toJson(this.companyRequest);
    MvcResult mvcResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.put("http://localhost:8080/api/company")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request))
            .andExpect(status().isCreated())
            .andReturn();

    assertNotNull(mvcResult.getResponse().getContentAsString());
  }
}
