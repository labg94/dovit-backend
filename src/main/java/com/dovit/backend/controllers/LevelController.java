package com.dovit.backend.controllers;

import com.dovit.backend.model.responses.LevelResponse;
import com.dovit.backend.services.LevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Ramón París
 * @since 10-12-2019
 */
@RequestMapping("/api")
@Secured({"ROLE_ADMIN","ROLE_CLIENT"})
@RequiredArgsConstructor
@RestController
public class LevelController {

    private final LevelService levelService;

    @GetMapping("/levels")
    public List<LevelResponse> findAll(){
        return  levelService.findAll();
    }

}
