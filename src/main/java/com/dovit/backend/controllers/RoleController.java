package com.dovit.backend.controllers;

import com.dovit.backend.annotations.IsAnyAdmin;
import com.dovit.backend.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ramón París
 * @since 01-07-20
 */
@RestController
@RequestMapping("/api")
@IsAnyAdmin
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class RoleController {

  private final RoleService roleService;

  @GetMapping("/roles")
  public ResponseEntity<?> findAll() {
    return ResponseEntity.ok(roleService.findAllButAdmin());
  }
}
