package com.dovit.backend.controllers;

import com.dovit.backend.domain.DevOpsCategory;
import com.dovit.backend.model.requests.CategoryRequest;
import com.dovit.backend.model.responses.ApiResponse;
import com.dovit.backend.model.responses.CategoryResponse;
import com.dovit.backend.services.DevOpsCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * @author Ramón París
 * @since 17-05-20
 */
@RequestMapping("/api")
@Secured({"ROLE_ADMIN", "ROLE_CLIENT"})
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@Slf4j
public class DevOpsCategoryController {

  private final DevOpsCategoryService devOpsCategoryService;

  @GetMapping("/categories/actives")
  public ResponseEntity<?> findAllActives() {
    return ResponseEntity.ok(devOpsCategoryService.findAllActives());
  }

  @GetMapping("/categories")
  public ResponseEntity<?> findAll() {
    return ResponseEntity.ok(devOpsCategoryService.findAll());
  }

  @GetMapping("/category/{id}")
  public ResponseEntity<?> findById(@PathVariable Long id) {
    CategoryResponse devOpsCategory = devOpsCategoryService.findById(id);
    return ResponseEntity.ok(devOpsCategory);
  }

  @PostMapping("/category")
  public ResponseEntity<?> save(@RequestBody @Valid CategoryRequest devOpsCategoryRequest) {
    DevOpsCategory devOpsCategory = devOpsCategoryService.save(devOpsCategoryRequest);
    log.info("DevOps Category with id {} created", devOpsCategory.getId());

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(devOpsCategory.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(new ApiResponse(true, "DevOps Category created successfully"));
  }

  @PutMapping("/category")
  public ResponseEntity<?> update(@RequestBody @Valid CategoryRequest devOpsCategoryRequest) {
    DevOpsCategory devOpsCategory = devOpsCategoryService.update(devOpsCategoryRequest);
    log.info("DevOps Category with id {} updated", devOpsCategory.getId());

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(devOpsCategory.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(new ApiResponse(true, "DevOps Category created successfully"));
  }

  @PatchMapping("/category/{id}")
  public ResponseEntity<?> toggleActive(@PathVariable Long id) {
    devOpsCategoryService.toggleActive(id);
    log.info("DevOps Category with id {} logic toggled", id);
    return ResponseEntity.ok(new ApiResponse(true, "DevOps Category toggled"));
  }
}
