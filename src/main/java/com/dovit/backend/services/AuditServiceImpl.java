package com.dovit.backend.services;

import com.dovit.backend.domain.Audit;
import com.dovit.backend.payloads.responses.AuditResponse;
import com.dovit.backend.repositories.AuditRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 16-10-2019
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuditServiceImpl implements AuditService {

  private final AuditRepository auditRepository;
  private final Gson gson;
  private final ModelMapper modelMapper;

  @Override
  @Transactional
  public void registerAudit(Object data, String message, String status, Long userId) {
    new Thread(
            () -> {
              String dataString = gson.toJson(data);
              try {
                auditRepository.registerAudit(dataString, message, status, userId);
              } catch (Exception e) {
                log.error(
                    "Error al ingresar la siguiente bitácora: {}. Message: {}. Status: {}. UserId: {}",
                    dataString,
                    message,
                    status,
                    userId);
              }
            })
        .start();
  }

  @Override
  public List<AuditResponse> findAllBetweenDates(LocalDateTime from, LocalDateTime to) {

    List<Audit> audits = auditRepository.findAllByActionDateBetween(from, to);
    return audits.stream()
        .map(u -> modelMapper.map(u, AuditResponse.class))
        .collect(Collectors.toList());
  }
}
