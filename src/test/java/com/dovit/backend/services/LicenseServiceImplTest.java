package com.dovit.backend.services;

import com.dovit.backend.config.ModelMapperConfig;
import com.dovit.backend.domain.License;
import com.dovit.backend.payloads.requests.LicenseRequest;
import com.dovit.backend.payloads.responses.LicenseResponse;
import com.dovit.backend.repositories.LicenseRepository;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.dovit.backend.util.DomainBuilderUtil.license;
import static com.dovit.backend.util.RequestBuilderUtil.getLicenseRequest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * @author Ramón París
 * @since 12-04-20
 */
@ExtendWith(SpringExtension.class)
class LicenseServiceImplTest {

  @InjectMocks LicenseServiceImpl licenseService;
  @Mock LicenseRepository licenseRepository;
  @Spy ModelMapper modelMapper = new ModelMapperConfig().modelMapper();

  @AfterEach
  void tearDown(TestInfo info) {
    if (!info.getTags().contains("SkipAfter")) modelMapper.validate();
  }

  @Test
  void findAllByToolId() {
    when(licenseRepository.findAllByToolId(anyLong()))
        .thenReturn(Collections.singletonList(license));
    List<LicenseResponse> responseList = licenseService.findAllByToolId(1L);
    assertNotNull(responseList);
    responseList.forEach(Assert::assertNotNull);
  }

  @Test
  @Tag("SkipAfter")
  void save() {
    when(licenseRepository.save(any(License.class))).thenAnswer(i -> i.getArgument(0));
    LicenseRequest request = getLicenseRequest();
    License response = licenseService.save(request);
    checkLicense(request, response);
  }

  @Test
  @Tag("SkipAfter")
  void update() {
    when(licenseRepository.save(any(License.class))).thenAnswer(i -> i.getArgument(0));
    when(licenseRepository.findById(anyLong())).thenReturn(Optional.of(license));
    LicenseRequest request = getLicenseRequest();
    License response = licenseService.update(request);
    checkLicense(request, response);
  }

  private void checkLicense(LicenseRequest request, License response) {
    assertNotNull(response);
    assertEquals(response.getId(), request.getLicenseId());
    assertEquals(response.getName(), request.getLicenseName());
    assertEquals(response.getObservation(), request.getLicenseObservation());
    assertEquals(response.getPayCycle().getId(), request.getLicensePayCycleId());
    assertEquals(response.getLicenseType().getId(), request.getLicenseTypeId());
  }
}
