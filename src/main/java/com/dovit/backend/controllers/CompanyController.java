package com.dovit.backend.controllers;

import com.dovit.backend.domain.Company;
import com.dovit.backend.model.requests.CompanyRequest;
import com.dovit.backend.model.responses.ApiResponse;
import com.dovit.backend.model.responses.CompanyResponse;
import com.dovit.backend.services.CompanyService;
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
 * @since 03-10-2019
 */
@RestController
@RequestMapping("/api")
@Secured("ROLE_ADMIN")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/companies")
    public List<CompanyResponse> findAll(){
        return companyService.findAll();
    }

    @Secured({"ROLE_CLIENT", "ROLE_ADMIN"})
    @GetMapping("/company/{companyId}")
    public CompanyResponse findById(@PathVariable Long companyId){
        return companyService.findCompanyResponseById(companyId);
    }

    @PostMapping("/company")
    public ResponseEntity<?> createCompany(@Valid @RequestBody CompanyRequest companyRequest){
        Company response = companyService.createCompany(companyRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Company created successfully"));

    }

    @PutMapping("/company")
    public ResponseEntity<?> updateCompany(@Valid @RequestBody CompanyRequest companyRequest){
        Company response = companyService.updateCompany(companyRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Company updated successfully"));

    }

}
