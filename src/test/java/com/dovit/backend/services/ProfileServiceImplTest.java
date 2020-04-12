package com.dovit.backend.services;

import com.dovit.backend.config.ModelMapperConfig;
import com.dovit.backend.model.responses.ProfileResponse;
import com.dovit.backend.repositories.ProfileRepository;
import com.dovit.backend.util.DomainBuilderUtil;
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

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * @author Ramón París
 * @since 12-04-20
 */
@ExtendWith(SpringExtension.class)
class ProfileServiceImplTest {

  @Spy ModelMapper modelMapper = new ModelMapperConfig().modelMapper();
  @InjectMocks ProfileServiceImpl profileService;
  @Mock ProfileRepository profileRepository;

  @Test
  void findAll() {
    when(profileRepository.findAll())
        .thenReturn(Collections.singletonList(DomainBuilderUtil.profile));
    List<ProfileResponse> responseList = profileService.findAll();
    assertNotNull(responseList);
    responseList.forEach(Assert::assertNotNull);
  }
}
