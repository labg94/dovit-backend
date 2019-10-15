package com.dovit.backend.controllers;

import com.dovit.backend.model.responses.CompanyLicensesResponse;
import com.dovit.backend.services.CompanyLicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Ramón París
 * @since 14-10-2019
 */
@RestController
@RequestMapping("/api")
@Secured("ROLE_ADMIN")
public class CompanyLicenseController {

    @Autowired
    private CompanyLicenseService companyLicenseService;

    @GetMapping("/company/{companyId}/tool/{toolId}")
    public List<CompanyLicensesResponse> findAllLicenses(@PathVariable Long companyId, @PathVariable Long toolId){
        return companyLicenseService.findAllByCompanyIdAndToolId(companyId, toolId);
    }

}
