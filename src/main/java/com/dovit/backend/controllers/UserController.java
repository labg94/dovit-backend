package com.dovit.backend.controllers;

import com.dovit.backend.domain.User;
import com.dovit.backend.model.requests.RegisterTokenRequest;
import com.dovit.backend.model.requests.UserRequest;
import com.dovit.backend.model.responses.ApiResponse;
import com.dovit.backend.model.responses.PagedResponse;
import com.dovit.backend.model.responses.UserResponse;
import com.dovit.backend.services.AuthService;
import com.dovit.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
@Secured("ROLE_ADMIN")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/users/admin")
    public PagedResponse<UserResponse> findAllAdmin(@RequestParam int page, @RequestParam int size){
        return userService.findAllAdmins(page, size);
    }

    @Secured({"ROLE_CLIENT", "ROLE_ADMIN"})
    @GetMapping("/company/{companyId}/users")
    public PagedResponse<UserResponse> findAllUsers(@Valid @PathVariable Long companyId, @RequestParam int page, @RequestParam int size){
        return userService.findAllClients(companyId, page, size);
    }

    @PostMapping("/user/token")
    public String createRegisterToken(@Valid @RequestBody RegisterTokenRequest request){
        return userService.createUserToken(request);
    }

    @GetMapping("/user/{userId}")
    public UserResponse findById(@PathVariable Long userId){
        return userService.findResponseById(userId);
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest){
        User response = userService.createUser(userRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User created successfully"));

    }

    @PutMapping("/user")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserRequest userRequest){
        User response = userService.updateUser(userRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User updated successfully"));
    }



}
