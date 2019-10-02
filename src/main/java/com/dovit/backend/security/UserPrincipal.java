package com.dovit.backend.security;

import com.dovit.backend.domain.Company;
import com.dovit.backend.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A custom "UserDetails" from Spring Security that has more attributes
 *
 * @author Ramón París
 */
public class UserPrincipal implements UserDetails {

    private Long id;
    private String name;
    private String lastName;
    private String email;
    private boolean active;
    private Company company;

    @JsonIgnore
    private String password;

    private GrantedAuthority authority;

    public UserPrincipal(Long id, String name, String lastName, String email, String password, GrantedAuthority authority, boolean active, Company company) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.authority = authority;
        this.active = active;
        this.company = company;
    }

    public static UserPrincipal create(User user) {

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName().name());

        return new UserPrincipal(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                authority,
                user.isActive(),
                user.getCompany()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public Company getCompany() {
        return company;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
