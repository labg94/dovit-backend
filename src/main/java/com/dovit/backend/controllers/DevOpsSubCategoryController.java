package com.dovit.backend.controllers;

import com.dovit.backend.annotations.IsAuthenticated;
import com.dovit.backend.annotations.IsMainAdmin;
import com.dovit.backend.domain.DevOpsSubcategory;
import com.dovit.backend.payloads.requests.SubCategoryRequest;
import com.dovit.backend.payloads.responses.ApiResponse;
import com.dovit.backend.services.DevOpsSubCategoryService;
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
public class DevOpsSubCategoryController {

  private final DevOpsSubCategoryService devOpsSubCategoryService;

  @IsAuthenticated
  @GetMapping("/category/{categoryId}/subcategories/actives")
  public ResponseEntity<?> findAllActivesByCategory(@PathVariable Long categoryId) {
    return ResponseEntity.ok(devOpsSubCategoryService.findAllActivesByCategoryId(categoryId));
  }

  @IsAuthenticated
  @GetMapping("/category/{categoryId}/subcategories")
  public ResponseEntity<?> findAllByCategory(@PathVariable Long categoryId) {
    return ResponseEntity.ok(devOpsSubCategoryService.findAllByCategoryId(categoryId));
  }

  @IsAuthenticated
  @GetMapping("/subcategories/actives")
  public ResponseEntity<?> findAllActives() {
    return ResponseEntity.ok(devOpsSubCategoryService.findAllActives());
  }

  @IsAuthenticated
  @GetMapping("/subcategory/{id}")
  public ResponseEntity<?> findById(@PathVariable Long id) {
    return ResponseEntity.ok(devOpsSubCategoryService.findResponseById(id));
  }

  @PostMapping("/subcategory")
  public ResponseEntity<?> save(@RequestBody @Valid SubCategoryRequest request) {
    DevOpsSubcategory devOpsCategory = devOpsSubCategoryService.save(request);
    log.info("DevOps subcategory with id {} created", devOpsCategory.getId());

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(devOpsCategory.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(
            ApiResponse.builder()
                .id(devOpsCategory.getId())
                .message("DevOps subcategory created successfull")
                .success(true)
                .build());
  }

  @PutMapping("/subcategory")
  public ResponseEntity<?> update(@RequestBody @Valid SubCategoryRequest request) {
    DevOpsSubcategory devOpsCategory = devOpsSubCategoryService.update(request);
    log.info("DevOps subcategory with id {} updated", devOpsCategory.getId());

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(devOpsCategory.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(
            ApiResponse.builder()
                .id(devOpsCategory.getId())
                .message("DevOps subcategory updated successfull")
                .success(true)
                .build());
  }

  @PatchMapping("/subcategory/{id}")
  public ResponseEntity<?> toggleActive(@PathVariable Long id) {
    devOpsSubCategoryService.toggleActive(id);
    log.info("DevOps subcategory with id {} logic toggled", id);
    return ResponseEntity.ok(new ApiResponse(true, "DevOps subcategory toggled"));
  }
}
