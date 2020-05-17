package com.dovit.backend.repositories;

import com.dovit.backend.domain.Role;
import com.dovit.backend.util.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Ramón París
 * @since 02-10-2019
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

  List<Role> findAllByNameIn(List<RoleName> names);
}
