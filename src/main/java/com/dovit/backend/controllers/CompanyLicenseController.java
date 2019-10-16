package com.dovit.backend.controllers;

import com.dovit.backend.domain.CompanyLicense;
import com.dovit.backend.model.requests.CompanyLicenseRequest;
import com.dovit.backend.model.responses.ApiResponse;
import com.dovit.backend.model.responses.CompanyLicensesResponse;
import com.dovit.backend.services.CompanyLicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * Controller to show the COMPANY LICENSES!
 * @author Ramón París
 * @since 14-10-2019
 */
@RestController
@RequestMapping("/api")
@Secured({"ROLE_ADMIN","ROLE_CLIENT"})
public class CompanyLicenseController {

    @Autowired
    private CompanyLicenseService companyLicenseService;

    @GetMapping("/company/{companyId}/tool/{toolId}")
    public List<CompanyLicensesResponse> findAllLicenses(@PathVariable Long companyId, @PathVariable Long toolId){
        return companyLicenseService.findAllByCompanyIdAndToolId(companyId, toolId);
    }

    @PostMapping("/company/license")
    public ResponseEntity<?> createCompanyLicense(@RequestBody @Valid CompanyLicenseRequest request){
        CompanyLicense response = companyLicenseService.createCompanyLicense(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Licencia asociada correctamente"));
    }

    @PutMapping("/company/license")
    public ResponseEntity<?> updateCompanyLicense(@RequestBody CompanyLicenseRequest request){
        CompanyLicense response = companyLicenseService.updateCompanyLicense(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "Licencia editada correctamente"));
    }

    @DeleteMapping("/company/license/{companyLicenseId}")
    public ResponseEntity<?> deleteCompanyLicense(@PathVariable Long companyLicenseId){
        Boolean resp = companyLicenseService.deleteCompanyLicense(companyLicenseId);
        return ResponseEntity.ok(new ApiResponse(resp, "Licencia eliminada"));
    }

}
