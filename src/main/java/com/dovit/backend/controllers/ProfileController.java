package com.dovit.backend.controllers;

import com.dovit.backend.model.responses.ProfileResponse;
import com.dovit.backend.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Ramón París
 * @since 10-12-2019
 */
@RequestMapping("/api")
@Secured({"ROLE_ADMIN", "ROLE_CLIENT"})
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/profiles")
    public List<ProfileResponse> findAll() {
        return profileService.findAll();
    }
}
