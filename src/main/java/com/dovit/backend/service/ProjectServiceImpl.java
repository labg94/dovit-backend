package com.dovit.backend.service;

import com.dovit.backend.domain.Project;
import com.dovit.backend.repository.ProjectRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final AuditService auditService;


    @Override
    public Project findById(Long id) {
        return null;
    }

    @Override
    public List<Project> findAll() {
        return null;
    }

    @Override
    public Project updateProject(Project project) {
        return null;
    }
}
