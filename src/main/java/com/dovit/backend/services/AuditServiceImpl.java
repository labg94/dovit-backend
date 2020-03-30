package com.dovit.backend.services;

import com.dovit.backend.repositories.AuditRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Ramón París
 * @since 16-10-2019
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;

    @Override
    public void registerAudit(Object data, String message, String status, Long userId) {
        new Thread(
                () -> {
                    try {
                        auditRepository.registerAudit(new Gson().toJson(data), message, status, userId);
                    } catch (Exception e) {
                        log.error("Error al ingresar la siguiente bitácora");
                    }
                })
                .start();
    }
}
