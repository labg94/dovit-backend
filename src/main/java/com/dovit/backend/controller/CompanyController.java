package com.dovit.backend.controller;

import com.dovit.backend.domain.Company;
import com.dovit.backend.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/company")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<Company> getId(@RequestParam Long id) {
        return ResponseEntity.ok(companyService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAll() {
        return ResponseEntity.ok(companyService.findAll());
    }

    @PutMapping
    public ResponseEntity<Company> update(@RequestBody Company company) {
        return ResponseEntity.ok(companyService.updateCompany(company));
    }
}
