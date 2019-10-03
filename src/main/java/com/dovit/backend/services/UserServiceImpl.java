package com.dovit.backend.services;

import com.dovit.backend.domain.Company;
import com.dovit.backend.domain.Role;
import com.dovit.backend.domain.User;
import com.dovit.backend.exceptions.BadRequestException;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.requests.RegisterTokenRequest;
import com.dovit.backend.model.requests.UserRequest;
import com.dovit.backend.model.responses.UserResponse;
import com.dovit.backend.repositories.UserRepository;
import com.dovit.backend.security.JwtAuthenticationFilter;
import com.dovit.backend.security.JwtTokenProvider;
import com.dovit.backend.security.UserPrincipal;
import com.dovit.backend.util.Constants;
import com.dovit.backend.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override
    public List<UserResponse> findAllAdmins(){
        List<User> users = userRepository.findAllByRoleId(Constants.ROLE_ADMIN_ID);
        return users.stream().map(u -> new UserResponse(
                u.getId(),
                u.getName(),
                u.getLastName(),
                u.getEmail(),
                u.getRole().getName().name(),
                u.getRole().getId(),
                u.isActive(),
                null,
                null
        )).collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> findAllClients(Long companyId) {
        JwtAuthenticationFilter.canActOnCompany(companyId);
        List<User> users = userRepository.findAllByCompanyId(companyId);
        return users.stream().map(u -> new UserResponse(
                u.getId(),
                u.getName(),
                u.getLastName(),
                u.getEmail(),
                u.getRole().getName().name(),
                u.getRole().getId(),
                u.isActive(),
                u.getCompany().getId(),
                u.getCompany().getName()
        )).collect(Collectors.toList());
    }

    @Override
    public User createUser(UserRequest userRequest) {
        checkValidRequest(userRequest);
        Company company = null;
        if (userRequest.getCompanyId() != null) {
            company = companyService.findById(userRequest.getCompanyId());
        }

        Role role = roleService.findById(userRequest.getRoleId());
        User user  = new User();
        ModelMapper.mapUserRequestToUser(userRequest, user);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setCompany(company);
        user.setRole(role);
        user = userRepository.save(user);
        return user;
    }

    @Override
    public User updateUser(UserRequest userRequest) {
        checkValidRequest(userRequest);
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

    public void checkValidRequest(UserRequest userRequest){
        if (userRequest.getRoleId().equals(Constants.ROLE_ADMIN_ID) && userRequest.getCompanyId() != null){
            throw new BadRequestException("Administrador no debe tener empresas asociadas");
        }

        if (userRequest.getRoleId().equals(Constants.ROLE_CLIENT_ID) && userRequest.getCompanyId() == null){
            throw new BadRequestException("Cliente debe tener empresa asociada");
        }
    }

    @Override
    public String createUserToken(RegisterTokenRequest registerTokenRequest) {
        Boolean exists = userRepository.existsByEmail(registerTokenRequest.getEmail());
        if (exists){
            throw new DataIntegrityViolationException("Detail:Email "+registerTokenRequest.getEmail() + " ya se encuentra en base de datos");
        }
        return tokenProvider.generateRegisterToken(registerTokenRequest);
    }
}
