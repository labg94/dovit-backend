package com.dovit.backend.controllers;

import com.dovit.backend.model.responses.CompanyResponse;
import com.dovit.backend.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<CompanyResponse> findAllBy(){
        return companyService.findAll();
    }

}
