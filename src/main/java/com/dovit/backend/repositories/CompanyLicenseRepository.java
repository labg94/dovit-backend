package com.dovit.backend.repositories;

import com.dovit.backend.domain.CompanyLicense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Ramón París
 * @since 14-10-2019
 */
public interface CompanyLicenseRepository extends JpaRepository<CompanyLicense, Long> {

  List<CompanyLicense> findAllByCompanyIdAndLicenseToolId(Long companyId, Long toolId);
}
