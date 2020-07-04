package com.dovit.backend.services;

import com.dovit.backend.config.ModelMapperConfig;
import com.dovit.backend.repositories.AuditRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
}
