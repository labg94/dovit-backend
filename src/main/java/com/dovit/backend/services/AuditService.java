package com.dovit.backend.services;

import com.dovit.backend.payloads.responses.AuditResponse;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Ramón París
 * @since 16-10-2019
 */
public interface AuditService {

  void registerAudit(Object data, String message, String status, Long userId);

  List<AuditResponse> findAllBetweenDates(LocalDateTime from, LocalDateTime to);
}
