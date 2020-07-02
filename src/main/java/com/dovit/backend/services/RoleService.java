package com.dovit.backend.services;

import com.dovit.backend.domain.Role;
import com.dovit.backend.payloads.responses.RoleResponse;

import java.util.List;

/**
 * @author Ramón París
 * @since 02-10-2019
 */
public interface RoleService {
  Role findById(Long id);

  List<RoleResponse> findAllButAdmin();
}
