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
    private Long companyId;
    private String companyName;

    @JsonIgnore
    private String password;

    private GrantedAuthority authority;

    public UserPrincipal(Long id, String name, String lastName, String email, String password, GrantedAuthority authority, boolean active, Long companyId, String companyName) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.authority = authority;
        this.active = active;
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public static UserPrincipal create(User user) {

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName().name());
        Long companyId = null;
        String companyName = null;
        if (user.getCompany() != null){
            companyId = user.getCompany().getId();
            companyName = user.getCompany().getName();
        }
        return new UserPrincipal(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                authority,
                user.isActive(),
                companyId,
                companyName
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

    public Long getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
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
