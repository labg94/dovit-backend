package com.dovit.backend.services;

import com.dovit.backend.domain.Company;

/**
 * @author Ramón París
 * @since 01-10-2019
 */
public interface CompanyService {

    Company findById(Long id);

}
