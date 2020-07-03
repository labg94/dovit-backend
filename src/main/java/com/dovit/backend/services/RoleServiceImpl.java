package com.dovit.backend.services;

import com.dovit.backend.domain.Role;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.payloads.responses.RoleResponse;
import com.dovit.backend.repositories.RoleRepository;
import com.dovit.backend.util.Constants;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 02-10-2019
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;
  private final ModelMapper modelMapper;

  @Override
  public Role findById(Long id) {
    return roleRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
  }

  @Override
  public List<RoleResponse> findAllButAdmin() {
    final List<Role> roles = roleRepository.findAll();
    return roles.stream()
        .filter(role -> !role.getId().equals(Constants.ROLE_ADMIN_ID))
        .map(role -> modelMapper.map(role, RoleResponse.class))
        .collect(Collectors.toList());
  }
}
