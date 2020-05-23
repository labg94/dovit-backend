package com.dovit.backend.services;

import com.dovit.backend.config.ModelMapperConfig;
import com.dovit.backend.domain.License;
import com.dovit.backend.domain.LicensePricing;
import com.dovit.backend.domain.Tool;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.requests.LicensePricingRequest;
import com.dovit.backend.model.requests.LicenseRequest;
import com.dovit.backend.model.requests.ToolRequest;
import com.dovit.backend.model.responses.ToolResponse;
import com.dovit.backend.repositories.ToolRepository;
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
import java.util.stream.IntStream;

import static com.dovit.backend.util.DomainBuilderUtil.gitlabTool;
import static com.dovit.backend.util.RequestBuilderUtil.getToolRequest;
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

  @Test
  @Tag("SkipAfter")
  void save() {
    when(toolRepository.save(any(Tool.class))).thenAnswer(i -> i.getArgument(0));
    ToolRequest toolRequest = getToolRequest();
    Tool tool = toolService.save(toolRequest);
    checkToolRequestMapping(toolRequest, tool);
  }

  private void checkToolRequestMapping(ToolRequest toolRequest, Tool tool) {
    assertNotNull(tool);
    assertEquals(tool.getName(), toolRequest.getName());
    assertEquals(tool.getDescription(), toolRequest.getDescription());

    assertEquals(toolRequest.getSubcategoryIds().size(), tool.getSubcategories().size());
    IntStream.range(0, tool.getSubcategories().size())
        .forEach(
            i ->
                assertEquals(
                    tool.getSubcategories().get(i).getId(),
                    toolRequest.getSubcategoryIds().get(i)));

    assertEquals(toolRequest.getLicenses().size(), tool.getLicenses().size());
    IntStream.range(0, tool.getLicenses().size())
        .forEach(
            i -> {
              final LicenseRequest licenseRequest = toolRequest.getLicenses().get(i);
              final License license = tool.getLicenses().get(i);

              assertEquals(license.getName(), licenseRequest.getLicenseName());
              assertEquals(license.getObservation(), licenseRequest.getLicenseObservation());
              assertEquals(license.getLicenseType().getId(), licenseRequest.getLicenseTypeId());
              assertEquals(license.getPayCycle().getId(), licenseRequest.getLicensePayCycleId());
              assertEquals(license.getTool(), tool);

              assertEquals(
                  license.getLicensePrices().size(), licenseRequest.getLicensePrices().size());
              IntStream.range(0, license.getLicensePrices().size())
                  .forEach(
                      j -> {
                        final LicensePricing licensePricing = license.getLicensePrices().get(j);
                        final LicensePricingRequest licensePricingRequest =
                            licenseRequest.getLicensePrices().get(j);

                        assertEquals(
                            licensePricing.getMaxUsers(), licensePricingRequest.getMaxUsers());
                        assertEquals(
                            licensePricing.getMinUsers(), licensePricingRequest.getMinUsers());
                        assertEquals(licensePricing.getPrice(), licensePricingRequest.getPrice());
                        assertEquals(licensePricing.getLicense(), license);
                      });
            });
  }
}
