package com.dovit.backend.repositories;

import com.dovit.backend.domain.LicensePayCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ramón París
 * @since 23-05-20
 */
@Repository
public interface LicensePayCycleRepository extends JpaRepository<LicensePayCycle, Long> {}
