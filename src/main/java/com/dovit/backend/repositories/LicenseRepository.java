package com.dovit.backend.repositories;

import com.dovit.backend.domain.License;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Ramón París
 * @since 14-10-2019
 */
public interface LicenseRepository extends JpaRepository<License, Long> {

  List<License> findAllByToolId(Long toolId);

  List<License> findAllByToolIdAndActive(Long toolId, boolean active);
}
