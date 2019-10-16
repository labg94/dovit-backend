package com.dovit.backend.repositories;

import com.dovit.backend.domain.DevOpsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Ramón París
 * @since 14-10-2019
 */
public interface DevOpsCategoryRepository extends JpaRepository<DevOpsCategory, Long> {

    @Query("SELECT DISTINCT cat FROM DevOpsCategory cat " +
            "JOIN cat.subcategories sub " +
            "JOIN sub.tools tools " +
            "JOIN tools.licenses license " +
            "JOIN license.companyLicenses companyLicense " +
            "WHERE companyLicense.company.id = :companyId " +
            "ORDER BY cat.id " )
    List<DevOpsCategory> findAllByCompanyId(Long companyId);

}
