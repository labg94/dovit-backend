package com.dovit.backend.services;

import com.dovit.backend.domain.Tool;
import com.dovit.backend.model.requests.ToolRequest;
import com.dovit.backend.model.responses.ToolResponse;

import java.util.List;

/**
 * @author Ramón París
 * @since 01-10-2019
 */
public interface ToolService {

  List<ToolResponse> findAllToolsOfCompany(Long companyId);

  List<ToolResponse> findAllTools();

  ToolResponse findById(Long toolId);

  Tool save(ToolRequest request);
}
