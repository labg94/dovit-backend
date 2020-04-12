package com.dovit.backend.services;

import com.dovit.backend.config.ModelMapperConfig;
import com.dovit.backend.model.responses.LicenseResponse;
import com.dovit.backend.repositories.LicenseRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static com.dovit.backend.util.DomainBuilderUtil.license;
import static org.junit.Assert.assertNotNull;
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

  @Test
  void findAllByToolId() {
    when(licenseRepository.findAllByToolId(anyLong()))
        .thenReturn(Collections.singletonList(license));
    List<LicenseResponse> responseList = licenseService.findAllByToolId(1L);
    assertNotNull(responseList);
    responseList.forEach(Assert::assertNotNull);
  }
}
