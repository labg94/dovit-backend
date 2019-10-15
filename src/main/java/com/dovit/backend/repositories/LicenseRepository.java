package com.dovit.backend.repositories;

import com.dovit.backend.domain.License;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ramón París
 * @since 14-10-2019
 */

public interface LicenseRepository extends JpaRepository<License, Long> {


}
