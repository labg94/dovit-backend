package com.dovit.backend.repositories;

import com.dovit.backend.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ramón París
 * @since 02-10-2019
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
