package com.dovit.backend.controllers;

import com.dovit.backend.services.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author Ramón París
 * @since 16-05-20
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Secured("ROLE_ADMIN")
@CrossOrigin(origins = "*")
public class AuditController {

  private final AuditService auditService;

  @GetMapping("/audits")
  public ResponseEntity<?> findAllByDates(
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime fromDate,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          LocalDateTime toDate,
      @RequestParam int page,
      @RequestParam int size) {

    return ResponseEntity.ok(auditService.findAllBetweenDates(fromDate, toDate, page, size));
  }
}
