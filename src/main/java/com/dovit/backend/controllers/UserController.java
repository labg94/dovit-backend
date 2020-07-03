package com.dovit.backend.controllers;

import com.dovit.backend.annotations.IsAnyAdmin;
import com.dovit.backend.annotations.IsAuthenticated;
import com.dovit.backend.domain.User;
import com.dovit.backend.payloads.requests.RegisterTokenRequest;
import com.dovit.backend.payloads.requests.UserRequest;
import com.dovit.backend.payloads.responses.ApiResponse;
import com.dovit.backend.payloads.responses.PagedResponse;
import com.dovit.backend.payloads.responses.UserResponse;
import com.dovit.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * @author Ramón París
 * @since 02-10-2019
 */
@RestController
@RequestMapping("/api")
@IsAnyAdmin
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/users/admin")
  public PagedResponse<UserResponse> findAllAdmin(@RequestParam int page, @RequestParam int size) {
    return userService.findAllAdmins(page, size);
  }

  @GetMapping("/users")
  public ResponseEntity<?> findAll() {
    return ResponseEntity.ok(userService.findAll());
  }

  @GetMapping("/company/{companyId}/users")
  public ResponseEntity<List<UserResponse>> findAll(@PathVariable Long companyId) {
    return ResponseEntity.ok(userService.findAllByCompanyId(companyId));
  }

  @PostMapping("/user/token")
  public String createRegisterToken(@Valid @RequestBody RegisterTokenRequest request) {
    return userService.createUserToken(request);
  }

  @IsAuthenticated
  @GetMapping("/user/{userId}")
  public UserResponse findById(@PathVariable Long userId) {
    return userService.findResponseById(userId);
  }

  @PostMapping("/user")
  public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest) {
    User response = userService.createUser(userRequest);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(new ApiResponse(true, "User created successfully"));
  }

  @IsAuthenticated
  @PutMapping("/user")
  public ResponseEntity<?> updateUser(@Valid @RequestBody UserRequest userRequest) {
    User response = userService.updateUser(userRequest);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(new ApiResponse(true, "User updated successfully"));
  }

  @PatchMapping("/user/{userId}")
  public ResponseEntity<?> toggleActiveUser(@PathVariable Long userId) {
    userService.toggleActive(userId);
    return ResponseEntity.ok(ApiResponse.builder().success(true).message("OK").build());
  }
}
