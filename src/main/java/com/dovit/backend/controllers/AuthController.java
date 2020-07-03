package com.dovit.backend.controllers;

import com.dovit.backend.domain.User;
import com.dovit.backend.payloads.requests.AuthRequest;
import com.dovit.backend.payloads.requests.AzureAuthRequest;
import com.dovit.backend.payloads.requests.SignUpRequest;
import com.dovit.backend.payloads.responses.ApiResponse;
import com.dovit.backend.payloads.responses.AuthResponse;
import com.dovit.backend.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

/**
 * @author Ramón París
 * @since 02-10-2019
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signIn")
  public ResponseEntity<?> signIn(@Valid @RequestBody AuthRequest authRequest) {
    AuthResponse token = authService.authenticateUser(authRequest);
    return ResponseEntity.ok(token);
  }

  @PostMapping("/azureSignIn")
  public ResponseEntity<?> signInByAzure(@Valid @RequestBody AzureAuthRequest azureAuthRequest) {
    AuthResponse token = authService.authenticateByAzure(azureAuthRequest);
    return ResponseEntity.ok(token);
  }

  @PostMapping("/signUp")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

    User result = authService.registerUser(signUpRequest);
    URI location =
        ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/users/{id}")
            .buildAndExpand(result.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(new ApiResponse(true, "User registered successfully"));
  }

  @GetMapping("/token")
  public ResponseEntity<?> getRegisterTokenInfo(@RequestParam String token) {
    return ResponseEntity.ok(authService.getRegisterTokenInfo(token));
  }
}
