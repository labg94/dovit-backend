package com.dovit.backend.services;

import com.dovit.backend.model.responses.MasterRegistryResponse;

import java.util.List;

/**
 * @author Ramón París
 * @since 31-05-20
 */
public interface ProjectTypeService {

  List<MasterRegistryResponse> findAll();
}
