package com.dovit.backend.controllers;

import com.dovit.backend.model.requests.LicenseRequest;
import com.dovit.backend.model.responses.CompanyLicensesResponse;
import com.dovit.backend.services.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Ramón París
 * @since 04-10-2019
 */
@RestController
@RequestMapping("/api")
@Secured("ROLE_ADMIN")
public class LicenseController {

    @Autowired
    private LicenseService licenseService;

    @GetMapping("/company/{companyId}/tool/{toolId}/licenses")
    public List<CompanyLicensesResponse> findAllByCompanyId(@PathVariable Long companyId, @PathVariable Long toolId){
        return null;
    }

    @GetMapping("/license/company/{licenseId}")
    public CompanyLicensesResponse findById(@PathVariable Long licenseId){
        return null;
    }

    @PostMapping("/company/{companyId}/license")
    public ResponseEntity<?> createLicense(@PathVariable Long companyId, @Valid @RequestBody LicenseRequest licenseRequest){
        return null;
    }

    @PutMapping("/company/{companyId}/license")
    public ResponseEntity<?> updateLicense(@PathVariable Long companyId, @Valid @RequestBody LicenseRequest licenseRequest){
        return null;
    }


}
