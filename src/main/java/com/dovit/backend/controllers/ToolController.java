package com.dovit.backend.controllers;

import com.dovit.backend.model.responses.DevopsCategoryResponse;
import com.dovit.backend.model.responses.ToolResponse;
import com.dovit.backend.services.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller to show the TOOLS in different ways
 * @author Ramón París
 * @since 14-10-2019
 */
@RestController
@RequestMapping("/api")
@Secured({"ROLE_ADMIN","ROLE_CLIENT"})
@CrossOrigin(origins = "*")
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
    public List<ToolResponse> findAllToolsByCompany(@PathVariable Long companyId) {
        return toolService.findAllToolsOfCompany(companyId);
    }

    @GetMapping("/tool/{toolId}")
    public ToolResponse findById(@PathVariable Long toolId){
        return toolService.findById(toolId);
    }


}
