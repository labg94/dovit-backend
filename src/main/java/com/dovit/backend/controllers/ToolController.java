package com.dovit.backend.controllers;

import com.dovit.backend.model.responses.DevopsCategoryResponse;
import com.dovit.backend.model.responses.ToolResponse;
import com.dovit.backend.services.ToolService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Ramón París
 * @since 14-10-2019
 */
@RestController
@RequestMapping("/api")
@Secured({"ROLE_ADMIN","ROLE_CLIENT"})
public class ToolController {

    @Autowired
    private ToolService toolService;

    @GetMapping("/tools")
    public List<DevopsCategoryResponse> findAllTools(){
        return null;
    }

    @GetMapping("/company/{companyId}/tools")
    public List<DevopsCategoryResponse> findAllToolsByCompany(@PathVariable Long companyId) {
        return toolService.findAllToolsOfCompany(companyId);
    };


}
