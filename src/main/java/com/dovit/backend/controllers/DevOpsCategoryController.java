package com.dovit.backend.controllers;

import com.dovit.backend.annotations.IsAuthenticated;
import com.dovit.backend.annotations.IsMainAdmin;
import com.dovit.backend.domain.DevOpsCategory;
import com.dovit.backend.payloads.requests.CategoryRequest;
import com.dovit.backend.payloads.responses.ApiResponse;
import com.dovit.backend.payloads.responses.CategoryResponse;
import com.dovit.backend.services.DevOpsCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * @author Ramón París
 * @since 17-05-20
 */
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@Slf4j
@IsMainAdmin
public class DevOpsCategoryController {

  private final DevOpsCategoryService devOpsCategoryService;

  @GetMapping("/categories/actives")
  @IsAuthenticated
  public ResponseEntity<?> findAllActives() {
    return ResponseEntity.ok(devOpsCategoryService.findAllActives());
  }

  @GetMapping("/categories")
  @IsAuthenticated
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
        .body(
            ApiResponse.builder()
                .id(devOpsCategory.getId())
                .message("DevOps category created successfull")
                .success(true)
                .build());
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
        .body(
            ApiResponse.builder()
                .id(devOpsCategory.getId())
                .message("DevOps category updated successfull")
                .success(true)
                .build());
  }

  @PatchMapping("/category/{id}")
  public ResponseEntity<?> toggleActive(@PathVariable Long id) {
    devOpsCategoryService.toggleActive(id);
    log.info("DevOps Category with id {} logic toggled", id);
    return ResponseEntity.ok(new ApiResponse(true, "DevOps Category toggled"));
  }
}
