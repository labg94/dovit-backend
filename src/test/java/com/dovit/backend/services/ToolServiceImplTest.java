package com.dovit.backend.services;

import com.dovit.backend.config.ModelMapperConfig;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.responses.ToolResponse;
import com.dovit.backend.repositories.ToolRepository;
import com.dovit.backend.util.ValidatorUtil;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

import static com.dovit.backend.util.DomainBuilderUtil.gitlabTool;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * @author Ramón París
 * @since 12-04-20
 */
@ExtendWith(SpringExtension.class)
class ToolServiceImplTest {

  @InjectMocks ToolServiceImpl toolService;
  @Mock ToolRepository toolRepository;
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
  void findAllToolsOfCompany() {
    when(toolRepository.findAllByCompanyId(anyLong()))
        .thenReturn(Collections.singletonList(gitlabTool));

    List<ToolResponse> responseList = toolService.findAllToolsOfCompany(1L);
    assertNotNull(responseList);
    responseList.forEach(
        object -> {
          Assert.assertNotNull(object);
          Assert.assertNotNull(object.getTags());
          Assert.assertTrue(object.getTags().size() > 0);
        });
  }

  @Test
  void findAllTools() {
    when(toolRepository.findAll()).thenReturn(Collections.singletonList(gitlabTool));

    List<ToolResponse> responseList = toolService.findAllTools();
    assertNotNull(responseList);
    responseList.forEach(
        object -> {
          Assert.assertNotNull(object);
          Assert.assertNotNull(object.getTags());
          Assert.assertTrue(object.getTags().size() > 0);
        });
  }

  @Test
  void findById_OK() {
    when(toolRepository.findById(anyLong())).thenReturn(Optional.of(gitlabTool));
    ToolResponse toolResponse = toolService.findById(1L);
    assertNotNull(toolResponse);
    Assert.assertNotNull(toolResponse);
    Assert.assertNotNull(toolResponse.getTags());
    Assert.assertTrue(toolResponse.getTags().size() > 0);
  }

  @Test
  void findById_EXCEPTION() {
    when(toolRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> toolService.findById(1L));
  }
}
