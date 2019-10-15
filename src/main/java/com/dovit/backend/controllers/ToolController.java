package com.dovit.backend.controllers;

import com.dovit.backend.model.responses.DevopsCategoryResponse;
import com.dovit.backend.model.responses.ToolResponse;
import com.dovit.backend.services.ToolService;
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
    public List<ToolResponse> findAll(){
        return toolService.findAllTools();
    }

    @GetMapping("/tools/categories")
    public List<DevopsCategoryResponse> findAllToolsGroupedBy(){
        return toolService.findAllToolsGroupedByCats();
    }

    @GetMapping("/company/{companyId}/tools")
    public List<DevopsCategoryResponse> findAllToolsByCompany(@PathVariable Long companyId) {
        return toolService.findAllToolsOfCompany(companyId);
    }


}
