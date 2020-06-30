package com.dovit.backend.repositories;

import com.dovit.backend.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Ramón París
 * @since 02-10-2019
 */
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  Page<User> findAllByRoleId(Long roleId, Pageable pageable);

  Page<User> findAllByCompanyId(Long companyId, Pageable pageable);

  List<User> findAllByCompanyId(Long companyId);

  Boolean existsByEmail(String email);
}
