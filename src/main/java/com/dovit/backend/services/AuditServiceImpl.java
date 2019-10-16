package com.dovit.backend.services;

import com.dovit.backend.repositories.AuditRepository;
import com.google.gson.Gson;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ramón París
 * @since 16-10-2019
 */
@Service
public class AuditServiceImpl implements AuditService {

    private static final Logger log = LoggerFactory.getLogger(AuditServiceImpl.class);

    @Autowired
    private AuditRepository auditRepository;

    @Override
    public void registerAudit(Object data, String message, String status, Long userId) {
        new Thread(()->{
            try {

                auditRepository.registerAudit(new Gson().toJson(data), message, status, userId);
            }catch (Exception e){
                log.error("Error al ingresar la siguiente bitácora");
            }
        }).start();

    }
}
