package com.dovit.backend.repositories;

import com.dovit.backend.domain.LicenseType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ramón París
 * @since 23-05-20
 */
public interface LicenseTypeRepository extends JpaRepository<LicenseType, Long> {}
