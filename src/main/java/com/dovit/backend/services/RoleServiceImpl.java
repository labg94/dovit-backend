package com.dovit.backend.services;

import com.dovit.backend.domain.Role;
import com.dovit.backend.exceptions.BadRequestException;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ramón París
 * @since 02-10-2019
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findById(Long id){
        return roleRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Role", "id", id));
    }

}
