package com.dovit.backend.services;

import com.dovit.backend.domain.Audit;
import com.dovit.backend.payloads.responses.AuditResponse;
import com.dovit.backend.payloads.responses.PagedResponse;
import com.dovit.backend.repositories.AuditRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
  public PagedResponse<AuditResponse> findAllBetweenDates(
      LocalDateTime from, LocalDateTime to, int page, int size) {

    Pageable pageable = PageRequest.of(page, size);
    Page<Audit> auditsPage = auditRepository.findAllByActionDateBetween(from, to, pageable);
    List<AuditResponse> auditResponses =
        auditsPage.getContent().stream()
            .map(u -> modelMapper.map(u, AuditResponse.class))
            .collect(Collectors.toList());

    return new PagedResponse<>(
        auditResponses,
        auditsPage.getNumber(),
        auditsPage.getSize(),
        auditsPage.getTotalElements(),
        auditsPage.getTotalPages(),
        auditsPage.isLast());
  }
}
