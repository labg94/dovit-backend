package com.dovit.backend.controllers;

import com.dovit.backend.annotations.IsMainAdmin;
import com.dovit.backend.services.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author Ramón París
 * @since 16-05-20
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@IsMainAdmin
@CrossOrigin(origins = "*")
public class AuditController {

  private final AuditService auditService;

  @GetMapping("/audits")
  public ResponseEntity<?> findAllByDates(
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime fromDate,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime toDate) {

    return ResponseEntity.ok(auditService.findAllBetweenDates(fromDate, toDate));
  }
}
