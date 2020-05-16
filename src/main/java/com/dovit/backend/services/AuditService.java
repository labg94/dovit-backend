package com.dovit.backend.services;

/**
 * @author Ramón París
 * @since 16-10-2019
 */
public interface AuditService {

  void registerAudit(Object data, String message, String status, Long userId);
}
