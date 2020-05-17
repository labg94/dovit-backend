package com.dovit.backend.repositories;

import com.dovit.backend.domain.Audit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

/**
 * @author Ramón París
 * @since 16-10-2019
 */
public interface AuditRepository extends JpaRepository<Audit, Long> {

  @Procedure("registerAudit")
  Boolean registerAudit(
      @Param("data") String data,
      @Param("message") String message,
      @Param("status") String status,
      @Param("user_id") Long user_id)
      throws Exception;

  Page<Audit> findAllByActionDateBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);
}
