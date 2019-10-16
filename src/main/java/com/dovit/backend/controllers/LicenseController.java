package com.dovit.backend.controllers;

import com.dovit.backend.model.LicenseDTO;
import com.dovit.backend.model.requests.LicenseRequest;
import com.dovit.backend.model.responses.CompanyLicensesResponse;
import com.dovit.backend.model.responses.LicenseResponse;
import com.dovit.backend.services.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller to show the general licenses of the tools. NOT ASSOCIATED WITH COMPANY
 * @author Ramón París
 * @since 04-10-2019
 */
@RestController
@RequestMapping("/api")
@Secured({"ROLE_ADMIN","ROLE_CLIENT"})
public class LicenseController {

    @Autowired
    private LicenseService licenseService;

    @GetMapping("/tool/{toolId}/licenses")
    public List<LicenseDTO> findAllLicensesOfTool(@PathVariable Long toolId){
        return licenseService.findAllByToolId(toolId);
    }


}
