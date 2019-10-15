package com.dovit.backend.services;

import com.dovit.backend.model.responses.DevopsCategoryResponse;

import java.util.List;

/**
 * @author Ramón París
 * @since 01-10-2019
 */
public interface ToolService {

    List<DevopsCategoryResponse> findAllToolsOfCompany(Long companyId);

    List<DevopsCategoryResponse> findAllTools();

}
