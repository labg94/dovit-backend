package com.dovit.backend.services;

import com.dovit.backend.domain.Company;
import com.dovit.backend.domain.Role;
import com.dovit.backend.domain.User;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.requests.UserRequest;
import com.dovit.backend.repositories.UserRepository;
import com.dovit.backend.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ramón París
 * @since 02-10-2019
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private RoleService roleService;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override
    public User createUser(UserRequest userRequest) {
        Company company = null;
        if (userRequest.getCompanyId() != null) {
            company = companyService.findById(userRequest.getCompanyId());
        }

        Role role = roleService.findById(userRequest.getRoleId());
        User user  = new User();
        ModelMapper.mapUserRequestToUser(userRequest, user);

        user.setCompany(company);
        user.setRole(role);
        user = userRepository.save(user);
        return user;
    }

    @Override
    public User updateUser(UserRequest userRequest) {
        User user = findById(userRequest.getId());
        ModelMapper.mapUserRequestToUser(userRequest, user);

        Company company = null;
        if (userRequest.getCompanyId() != null) {
            company = companyService.findById(userRequest.getCompanyId());
        }

        Role role = roleService.findById(userRequest.getRoleId());
        user.setCompany(company);
        user.setRole(role);
        user = userRepository.save(user);
        return user;
    }
}
