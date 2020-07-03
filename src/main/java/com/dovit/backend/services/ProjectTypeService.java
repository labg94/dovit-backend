package com.dovit.backend.services;

import com.dovit.backend.domain.ProjectType;
import com.dovit.backend.payloads.requests.ProjectTypeRequest;
import com.dovit.backend.payloads.responses.MasterRegistryResponse;

import java.util.List;

/**
 * @author Ramón París
 * @since 31-05-20
 */
public interface ProjectTypeService {

  List<MasterRegistryResponse> findAll();

  ProjectType save(ProjectTypeRequest request);

  ProjectType update(ProjectTypeRequest request);
}
