package com.dovit.backend.service;

import com.dovit.backend.domain.Project;
import com.dovit.backend.domain.Tool;

import java.util.List;

public interface ToolService {

    Tool findById(Long id);

    List<Tool> findAll();

    Tool updateTool(Tool tool);

    List<Tool> generatePipeline(Project project);
}
