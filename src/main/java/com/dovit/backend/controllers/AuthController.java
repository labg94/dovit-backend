package com.dovit.backend.controllers;

import com.dovit.backend.domain.User;
import com.dovit.backend.model.requests.AuthRequest;
import com.dovit.backend.model.requests.RegisterTokenRequest;
import com.dovit.backend.model.requests.SignUpRequest;
import com.dovit.backend.model.responses.ApiResponse;
import com.dovit.backend.model.responses.AuthResponse;
import com.dovit.backend.services.AuthService;
import com.dovit.backend.services.EmailService;
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
    private final EmailService emailService;

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@Valid @RequestBody AuthRequest authRequest) {
        AuthResponse token = authService.authenticateUser(authRequest);
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
    public RegisterTokenRequest getRegisterTokenInfo(@RequestParam String token) {
        return authService.getRegisterTokenInfo(token);
    }

    @GetMapping("/hello-world")
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping("/mail")
    public String testMail() {
        emailService.sendSimpleMessage("pariis78@gmail.com", "TEST!", "Viva chavez!!!!");
        return "sent!!";
    }
}
