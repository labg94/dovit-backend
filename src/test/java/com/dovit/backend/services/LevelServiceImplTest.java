package com.dovit.backend.services;

import com.dovit.backend.config.ModelMapperConfig;
import com.dovit.backend.model.responses.LevelResponse;
import com.dovit.backend.repositories.LevelRepository;
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

import static com.dovit.backend.util.DomainBuilderUtil.level;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * @author Ramón París
 * @since 12-04-20
 */
@ExtendWith(SpringExtension.class)
class LevelServiceImplTest {

  @InjectMocks LevelServiceImpl levelService;
  @Mock LevelRepository levelRepository;
  @Spy ModelMapper modelMapper = new ModelMapperConfig().modelMapper();

  @Test
  void findAll() {
    when(levelRepository.findAll()).thenReturn(Collections.singletonList(level));
    List<LevelResponse> responseList = levelService.findAll();
    assertNotNull(responseList);
    responseList.forEach(Assert::assertNotNull);
  }
}
