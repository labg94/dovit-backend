package com.dovit.backend.services;

import com.dovit.backend.config.ModelMapperConfig;
import com.dovit.backend.domain.CompanyLicense;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.responses.CompanyLicensesResponse;
import com.dovit.backend.repositories.CompanyLicenseRepository;
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

import static com.dovit.backend.util.DomainBuilderUtil.companyLicense;
import static com.dovit.backend.util.RequestBuilderUtil.companyLicenseRequest;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * @author Ramón París
 * @since 05-04-20
 */
@ExtendWith(SpringExtension.class)
class CompanyLicenseServiceImplTest {

  @InjectMocks CompanyLicenseServiceImpl companyLicenseService;
  @Mock ValidatorUtil validatorUtil;
  @Spy ModelMapper modelMapper = new ModelMapperConfig().modelMapper();
  @Mock CompanyLicenseRepository companyLicenseRepository;

  @BeforeEach
  void setUp() {
    when(validatorUtil.canActOnCompany(anyLong())).thenReturn(true);
  }

  @AfterEach
  void tearDown(TestInfo info) {
    if (!info.getTags().contains("SkipAfter")) modelMapper.validate();
  }

  @Test
  void findAllByCompanyIdAndToolId() {
    when(companyLicenseRepository.findAllByCompanyIdAndLicenseToolIdOrderByStartDateDesc(
            anyLong(), anyLong()))
        .thenReturn(Collections.singletonList(companyLicense));

    List<CompanyLicensesResponse> responseList =
        companyLicenseService.findAllByCompanyIdAndToolId(1L, 1L);

    assertNotNull(responseList);
    responseList.forEach(Assert::assertNotNull);
  }

  @Test
  @Tag("SkipAfter")
  void createCompanyLicense() {
    when(companyLicenseRepository.save(any())).thenReturn(companyLicense);
    CompanyLicense response = companyLicenseService.createCompanyLicense(companyLicenseRequest);

    assertNotNull(response);
    assertEquals(response, companyLicense);
  }

  @Test
  void updateCompanyLicense_OK() {
    when(companyLicenseRepository.findById(anyLong())).thenReturn(Optional.of(companyLicense));
    when(companyLicenseRepository.save(any())).thenAnswer(i -> i.getArgument(0));
    CompanyLicense response = companyLicenseService.updateCompanyLicense(companyLicenseRequest);

    assertNotNull(response);
    assertEquals(response.getExpirationDate(), companyLicenseRequest.getExpirationDate());
    assertEquals(response.getStartDate(), companyLicenseRequest.getStartDate());
  }

  @Test
  void updateCompanyLicense_EXCEPTION() {
    when(companyLicenseRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(
        ResourceNotFoundException.class,
        () -> companyLicenseService.updateCompanyLicense(companyLicenseRequest));
  }

  @Test
  void deleteCompanyLicense_OK() {
    when(companyLicenseRepository.findById(anyLong())).thenReturn(Optional.of(companyLicense));
    doNothing().when(companyLicenseRepository).delete(any(CompanyLicense.class));

    boolean response = companyLicenseService.deleteCompanyLicense(1L);
    assertTrue(response);
  }

  @Test
  void deleteCompanyLicense_EXCEPTION() {
    when(companyLicenseRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(
        ResourceNotFoundException.class, () -> companyLicenseService.deleteCompanyLicense(1L));
  }
}
