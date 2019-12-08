package com.dovit.backend.services;

import com.dovit.backend.domain.Company;
import com.dovit.backend.domain.Role;
import com.dovit.backend.domain.User;
import com.dovit.backend.exceptions.BadRequestException;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.model.requests.RegisterTokenRequest;
import com.dovit.backend.model.requests.UserRequest;
import com.dovit.backend.model.responses.PagedResponse;
import com.dovit.backend.model.responses.UserResponse;
import com.dovit.backend.repositories.UserRepository;
import com.dovit.backend.security.JwtAuthenticationFilter;
import com.dovit.backend.security.JwtTokenProvider;
import com.dovit.backend.security.UserPrincipal;
import com.dovit.backend.util.Constants;
import com.dovit.backend.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private EmailService emailService;

    @Value("${app.frontend.domain}")
    private String APP_FRONTEND_DOMAIN;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override
    public PagedResponse<UserResponse> findAllAdmins(int page, int size){
        Pageable pagination = PageRequest.of(page, size);
        Page<User> usersPage = userRepository.findAllByRoleId(Constants.ROLE_ADMIN_ID, pagination);
        List<UserResponse> users = usersPage.getContent().stream().map(u -> new UserResponse(
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


        return new PagedResponse<>(users, usersPage.getNumber(), usersPage.getSize(), usersPage.getTotalElements(), usersPage.getTotalPages(), usersPage.isLast());
    }

    @Override
    public PagedResponse<UserResponse> findAllClients(Long companyId, int page, int size) {
        JwtAuthenticationFilter.canActOnCompany(companyId);
        Pageable pagination = PageRequest.of(page, size);
        Page<User> usersPage = userRepository.findAllByCompanyId(companyId, pagination);
        List<UserResponse> users = usersPage.getContent().stream().map(u -> new UserResponse(
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


        return new PagedResponse<>(users, usersPage.getNumber(), usersPage.getSize(), usersPage.getTotalElements(), usersPage.getTotalPages(), usersPage.isLast());
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

        String token = tokenProvider.generateRegisterToken(registerTokenRequest);
        emailService.sendSimpleMessage(registerTokenRequest.getEmail(), "Registration process",
                "Bienvenido a Dovit! Ingresa al siguiente link y termina tu registro: " + APP_FRONTEND_DOMAIN + "/userCompany/create/" + token);
        return token;
    }

    @Override
    public UserResponse findResponseById(Long userId) {
        User u = this.findById(userId);

        return new UserResponse(
                u.getId(),
                u.getName(),
                u.getLastName(),
                u.getEmail(),
                u.getRole().getName().name(),
                u.getRole().getId(),
                u.isActive(),
                u.getCompany() != null ? u.getCompany().getId() : null,
                u.getCompany() != null ? u.getCompany().getName() : null
        );
    }
}
