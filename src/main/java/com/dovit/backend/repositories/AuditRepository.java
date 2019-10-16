package com.dovit.backend.repositories;

import com.dovit.backend.domain.Audit;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Ramón París
 * @since 16-10-2019
 */

public interface AuditRepository extends JpaRepository<Audit, Long> {

    @Procedure("registerAudit")
    Boolean registerAudit(@Param("data") String data, @Param("message") String message, @Param("status") String status, @Param("user_id") Long user_id);

}