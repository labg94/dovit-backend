package com.dovit.backend.controllers;

import com.dovit.backend.services.LevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ramón París
 * @since 10-12-2019
 */
@RequestMapping("/api")
@Secured({"ROLE_ADMIN", "ROLE_CLIENT"})
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
public class LevelController {

  private final LevelService levelService;

  @GetMapping("/levels")
  public ResponseEntity<?> findAll() {
    return ResponseEntity.ok(levelService.findAll());
  }
}
