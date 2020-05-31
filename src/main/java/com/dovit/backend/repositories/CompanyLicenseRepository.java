package com.dovit.backend.repositories;

import com.dovit.backend.domain.CompanyLicense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Ramón París
 * @since 14-10-2019
 */
public interface CompanyLicenseRepository extends JpaRepository<CompanyLicense, Long> {

  List<CompanyLicense> findAllByCompanyIdAndLicenseToolIdOrderByStartDateDesc(
      Long companyId, Long toolId);

  @Query(
      "select cl from CompanyLicense cl"
          + " join Company company on company = cl.company "
          + " join License license on license = cl.license "
          + " join Tool tool on tool = license.tool "
          + "where cl.company.id = :companyId "
          + "and cl.license.id = :licenseId ")
  List<CompanyLicense> findAllLicensesByCompanyIdAndLicenseId(Long companyId, Long licenseId);
}
