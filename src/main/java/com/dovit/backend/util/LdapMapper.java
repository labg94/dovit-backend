package com.dovit.backend.util;

import com.dovit.backend.domain.Company;
import com.dovit.backend.domain.Role;
import com.dovit.backend.domain.User;
import org.springframework.ldap.core.AttributesMapper;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

/**
 * @author Ramón París
 * @since 07-12-2019
 */
public class LdapMapper implements AttributesMapper {

    @Override
    public Object mapFromAttributes(Attributes attributes) throws NamingException {
        //TODO: El role que se toma no debería ser estático. Debería ser dinámico según lo que entregue el LDAP pero no sube hacer la relación de a
        // qué grupo pertenece este miembro
        Role role = new Role();
        role.setName(RoleName.ROLE_ADMIN);

        User user = new User();
        user.setCompany(null);
        user.setRole(role);
        user.setActive(true);
        user.setEmail((String) attributes.get("mail").get());
        user.setName((String) attributes.get("givenname").get());
        user.setLastName((String) attributes.get("sn").get());
        user.setId(0L);

        return user;
    }
}
