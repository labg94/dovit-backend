package com.dovit.backend.service;

import com.dovit.backend.domain.Audit;
import com.dovit.backend.repository.AuditRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;

    @Override
    public Audit save(Audit audit) {
        return null;
    }
}
