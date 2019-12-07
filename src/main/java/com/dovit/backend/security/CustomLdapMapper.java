package com.dovit.backend.security;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;

import java.util.Collection;

/**
 * @author Ramón París
 * @since 07-12-2019
 */
public class CustomLdapMapper extends LdapUserDetailsMapper {

    @Override
    public CustomLdapUserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {
        LdapUserDetailsImpl userDetails = (LdapUserDetailsImpl) super.mapUserFromContext(ctx, username, authorities);
        CustomLdapUserDetails asd = new CustomLdapUserDetails(userDetails);
        asd.setFirstName(ctx.getStringAttribute("givenName"));
        asd.setLastName(ctx.getStringAttribute("sn"));
        return asd;
    }
}
