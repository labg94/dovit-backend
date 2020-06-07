package com.dovit.backend.services;

import com.dovit.backend.config.ModelMapperConfig;
import com.dovit.backend.domain.Company;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.payloads.responses.CompanyResponse;
import com.dovit.backend.repositories.CompanyRepository;
import com.dovit.backend.util.ValidatorUtil;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.dovit.backend.util.DomainBuilderUtil.company;
import static com.dovit.backend.util.RequestBuilderUtil.companyRequest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * @author Ramón París
 * @since 12-04-20
 */
@ExtendWith(SpringExtension.class)
class CompanyServiceImplTest {

  @InjectMocks CompanyServiceImpl companyService;
  @Mock CompanyRepository companyRepository;
  @Mock ValidatorUtil validatorUtil;
  @Spy ModelMapper modelMapper = new ModelMapperConfig().modelMapper();

  @BeforeEach
  void setUp() {
    when(validatorUtil.canActOnCompany(anyLong())).thenReturn(true);
  }

  @AfterEach
  void tearDown(TestInfo info) {
    if (!info.getTags().contains("SkipAfter")) modelMapper.validate();
  }

  @Test
  void findById_OK() {
    when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
    Company response = companyService.findById(1L);
    assertNotNull(response);
    assertEquals(response, company);
  }

  @Test
  void findById_EXCEPTION() {
    when(companyRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> companyService.findById(1L));
  }

  @Test
  void findCompanyResponseById() {
    when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
    CompanyResponse response = companyService.findCompanyResponseById(1L);
    assertNotNull(response);
    assertEquals(company.getName(), response.getName());
  }

  @Test
  void findAll() {
    when(companyRepository.findAll()).thenReturn(Collections.singletonList(company));
    List<CompanyResponse> responses = companyService.findAll();

    assertNotNull(responses);
    responses.forEach(Assert::assertNotNull);
  }

  @Test
  @Tag("SkipAfter")
  void createCompany() {
    when(companyRepository.save(any())).thenAnswer(i -> i.getArgument(0));
    Company response = companyService.createCompany(companyRequest);
    assertNotNull(response);
  }

  @Test
  @Tag("SkipAfter")
  void updateCompany() {
    when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
    when(companyRepository.save(any())).thenAnswer(i -> i.getArgument(0));
    Company response = companyService.updateCompany(companyRequest);
    assertNotNull(response);
    assertEquals(response.getName(), companyRequest.getName());
  }
}
