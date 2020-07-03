package com.dovit.backend.repositories;

import com.dovit.backend.domain.LicensePricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ramón París
 * @since 23-05-20
 */
@Repository
public interface LicensePricingRepository extends JpaRepository<LicensePricing, Long> {}
