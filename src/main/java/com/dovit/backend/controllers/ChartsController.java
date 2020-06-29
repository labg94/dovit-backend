package com.dovit.backend.controllers;

import com.dovit.backend.annotations.IsAuthenticated;
import com.dovit.backend.services.ChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ramón París
 * @since 28-06-20
 */
@RestController
@IsAuthenticated
@RequiredArgsConstructor
@RequestMapping("/api/company/{companyId}")
public class ChartsController {

    private final ChartService chartService;

    @GetMapping("/chart/seniors")
    public ResponseEntity<?> findTopSeniorMembers(@PathVariable Long companyId) {
        return ResponseEntity.ok(chartService.findTopSeniorMembers(companyId));
    }

}
