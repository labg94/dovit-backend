package com.dovit.backend.services;

import com.dovit.backend.payloads.responses.AuditResponse;
import com.dovit.backend.payloads.responses.PagedResponse;

import java.time.LocalDateTime;

/**
 * @author Ramón París
 * @since 16-10-2019
 */
public interface AuditService {

  void registerAudit(Object data, String message, String status, Long userId);

  PagedResponse<AuditResponse> findAllBetweenDates(
      LocalDateTime from, LocalDateTime to, int page, int size);
}
