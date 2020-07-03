package com.dovit.backend.repositories;

import com.dovit.backend.domain.Holding;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ramón París
 * @since 23-06-20
 */
public interface HoldingRepository extends JpaRepository<Holding, Long> {}
