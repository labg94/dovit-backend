package com.dovit.backend.services;

import com.dovit.backend.config.ModelMapperConfig;
import com.dovit.backend.domain.Audit;
import com.dovit.backend.domain.User;
import com.dovit.backend.payloads.responses.AuditResponse;
import com.dovit.backend.payloads.responses.PagedResponse;
import com.dovit.backend.repositories.AuditRepository;
import com.dovit.backend.util.Constants;
import com.dovit.backend.util.DomainBuilderUtil;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * @author Ramón París
 * @since 05-04-20
 */
@ExtendWith(SpringExtension.class)
class AuditServiceImplTest {

  @InjectMocks AuditServiceImpl auditService;
  @Mock AuditRepository auditRepository;
  @Spy ModelMapper modelMapper = new ModelMapperConfig().modelMapper();

  @AfterEach
  void tearDown() {
    modelMapper.validate();
  }

  @Test
  void registerAudit_success() throws Exception {
    when(auditRepository.registerAudit(anyString(), anyString(), anyString(), anyLong()))
        .thenReturn(true);
    auditService.registerAudit(new Object(), "Message", "Status", 1L);
  }

  @Test
  void registerAudit_exception() throws Exception {
    when(auditRepository.registerAudit(anyString(), anyString(), anyString(), anyLong()))
        .thenThrow(Exception.class);
    auditService.registerAudit(new Object(), "Message", "Status", 1L);
  }

  @Test
  void findAllBetweenDates() {
    when(auditRepository.findAllByActionDateBetween(
            any(LocalDateTime.class), any(LocalDateTime.class), any(Pageable.class)))
        .thenReturn(
            new PageImpl<>(
                Collections.singletonList(
                    Audit.builder()
                        .id(1L)
                        .user(
                            User.builder()
                                .id(1L)
                                .email("pariis78@gmail.com")
                                .name("Ramón")
                                .lastName("París")
                                .build())
                        .actionDate(LocalDateTime.now())
                        .data(DomainBuilderUtil.repositories)
                        .message("INICIO DE SESION")
                        .status(Constants.AUDIT_STATUS_NOK)
                        .build())));

    PagedResponse<AuditResponse> response =
        auditService.findAllBetweenDates(LocalDateTime.now(), LocalDateTime.now(), 0, 25);

    assertNotNull(response);
    response.getContent().forEach(Assert::assertNotNull);
  }
}
