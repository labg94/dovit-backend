package com.dovit.backend.services;

import com.dovit.backend.repositories.AuditRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

/**
 * @author Ramón París
 * @since 05-04-20
 */
@ExtendWith(SpringExtension.class)
class AuditServiceImplTest {

  @InjectMocks AuditServiceImpl auditService;
  @Mock AuditRepository auditRepository;

  @Test
  void registerAudit_success() throws Exception {
    Mockito.when(auditRepository.registerAudit(anyString(), anyString(), anyString(), anyLong()))
        .thenReturn(true);
    auditService.registerAudit(new Object(), "Message", "Status", 1L);
  }

  @Test
  void registerAudit_exception() throws Exception {
    Mockito.when(auditRepository.registerAudit(anyString(), anyString(), anyString(), anyLong()))
        .thenThrow(Exception.class);
    auditService.registerAudit(new Object(), "Message", "Status", 1L);
  }
}
