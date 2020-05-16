package com.dovit.backend.util;

import com.dovit.backend.exceptions.CustomAccessDeniedException;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.security.UserPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author Ramón París
 * @since 30-03-20
 */
@Component
public class ValidatorUtil {

  public Boolean canActOnCompany(Long companyId) {
    UserPrincipal userPrincipal =
        (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String roleName =
        userPrincipal.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .findFirst()
            .orElseThrow(() -> new ResourceNotFoundException("Role", "name", ""));
    if (RoleName.ROLE_ADMIN.name().equals(roleName)) return true;

    if (companyId.equals(userPrincipal.getCompanyId())) {
      return true;
    } else {
      throw new CustomAccessDeniedException("Access denied");
    }
  }
}
