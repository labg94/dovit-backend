package com.dovit.backend.services;

import com.dovit.backend.domain.Role;

/**
 * @author Ramón París
 * @since 02-10-2019
 */
public interface RoleService {
  Role findById(Long id);
}
