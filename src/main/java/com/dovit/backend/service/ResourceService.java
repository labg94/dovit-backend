package com.dovit.backend.service;

import com.dovit.backend.domain.Resource;

import java.util.List;

public interface ResourceService {

    Resource findById(Long id);

    List<Resource> findAll();

    Resource updateResource(Resource resource);
}
