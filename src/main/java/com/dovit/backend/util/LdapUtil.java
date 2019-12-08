package com.dovit.backend.util;

import com.dovit.backend.domain.User;
import com.dovit.backend.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Ramón París
 * @since 07-12-2019
 */
@Component
public class LdapUtil {

    @Autowired
    private LdapTemplate ldapTemplate;

    public UserDetails findDataByUsername(String username){

        List<? extends User> users = ldapTemplate.search("ou=people","uid="+username, new LdapMapper());
        return UserPrincipal.create(users.get(0));

    }


}
