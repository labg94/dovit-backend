package com.dovit.backend.repositories;

import com.dovit.backend.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Ramón París
 * @since 02-10-2019
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {

  List<Company> findAllByActive(boolean active);
}
