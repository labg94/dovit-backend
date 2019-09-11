package com.dovit.backend.service;

import com.dovit.backend.domain.Project;
import com.dovit.backend.domain.Tool;
import com.dovit.backend.repository.ToolRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ToolServiceImpl implements ToolService {

    private final ToolRepository toolRepository;
    private final AuditService auditService;


    @Override
    public Tool findById(Long id) {
        return null;
    }

    @Override
    public List<Tool> findAll() {
        return null;
    }

    @Override
    public Tool updateTool(Tool tool) {
        return null;
    }

    @Override
    public List<Tool> generatePipeline(Project project) {
        return null;
    }
}
