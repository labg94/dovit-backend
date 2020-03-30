package com.dovit.backend.services;

import com.dovit.backend.domain.Role;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Ramón París
 * @since 02-10-2019
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findById(Long id) {
        return roleRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
    }
}
