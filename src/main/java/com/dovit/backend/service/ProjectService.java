package com.dovit.backend.service;

import com.dovit.backend.domain.Project;

import java.util.List;

public interface ProjectService {

    Project findById(Long id);

    List<Project> findAll();

    Project updateProject(Project project);
}
