package com.dovit.backend.services;

import com.dovit.backend.payloads.requests.ToolProjectTypeRequest;
import com.dovit.backend.payloads.responses.ProjectTypeResponse;

import java.util.List;

/**
 * @author Ramón París
 * @since 08-06-20
 */
public interface ToolProjectTypeService {

  List<ProjectTypeResponse> findAllByToolId(Long toolId);

  void save(ToolProjectTypeRequest toolProjectTypeRequest);

  void delete(Long toolId, Long projectTypeId);
}
