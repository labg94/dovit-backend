package com.dovit.backend.util;

import com.dovit.backend.domain.User;
import com.dovit.backend.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Ramón París
 * @since 07-12-2019
 */
@Component
@RequiredArgsConstructor
public class LdapUtil {

  private final LdapTemplate ldapTemplate;

  public UserDetails findDataByUsername(String mail) {

    List<? extends User> users = ldapTemplate.search("ou=people", "mail=" + mail, new LdapMapper());
    return UserPrincipal.create(users.get(0));
  }
}
