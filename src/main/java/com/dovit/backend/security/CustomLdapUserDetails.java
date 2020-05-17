package com.dovit.backend.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;

import java.util.Collection;

/**
 * @author Ramón París
 * @since 07-12-2019
 */
@Data
public class CustomLdapUserDetails implements UserDetails {

    private String dn;
    private String password;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    private String firstName;
    private String lastName;

    public CustomLdapUserDetails(LdapUserDetailsImpl details) {
        this.setDn(details.getDn());
        this.setPassword(details.getPassword());
        this.setUsername(details.getUsername());
        this.setAuthorities(details.getAuthorities());
        this.setAccountNonExpired(details.isAccountNonExpired());
        this.setAccountNonLocked(details.isAccountNonLocked());
        this.setCredentialsNonExpired(details.isCredentialsNonExpired());
        this.setEnabled(details.isEnabled());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
