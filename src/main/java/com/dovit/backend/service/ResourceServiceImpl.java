package com.dovit.backend.service;

import com.dovit.backend.domain.Resource;
import com.dovit.backend.repository.ResourceRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository resourceRepository;
    private final AuditService auditService;


    @Override
    public Resource findById(Long id) {
        return null;
    }

    @Override
    public List<Resource> findAll() {
        return null;
    }

    @Override
    public Resource updateResource(Resource resource) {
        return null;
    }
}
