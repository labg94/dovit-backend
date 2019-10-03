package com.dovit.backend.repositories;

import com.dovit.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Ramón París
 * @since 02-10-2019
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findAllByRoleId(Long roleId);

    List<User> findAllByCompanyId(Long companyId);

    Boolean existsByEmail(String email);

}
