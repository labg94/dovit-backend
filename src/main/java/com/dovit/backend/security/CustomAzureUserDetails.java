package com.dovit.backend.security;

import com.dovit.backend.domain.Company;
import com.dovit.backend.domain.Role;
import com.dovit.backend.domain.User;
import com.dovit.backend.exceptions.UnauthorizedException;
import com.dovit.backend.util.RoleName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

/**
 * @author Ramón París
 * @since 22-06-20
 */
@Component
public class CustomAzureUserDetails {

  @Value("${azure.activedirectory.user-group.key}")
  private String AZURE_ADMIN_GROUP_ID;

  public UserDetails createUserDetailByAzure(String token) {
    try {
      final JsonNode node =
          new ObjectMapper()
              .readTree(new String(Base64Utils.decodeFromString(token.split("\\.")[1])));

      if (!node.findValue("roles").toString().contains(AZURE_ADMIN_GROUP_ID)) {
        throw new UnauthorizedException(
            String.format(
                "%s unauthorized access to Dovit", node.findValue("preferred_username").asText()),
            node.findValue("name").asText(),
            node.findValue("preferred_username").asText());
      }

      final Role ROLE = Role.builder().name(RoleName.ROLE_ADMIN).build();
      User user =
          User.builder()
              .role(ROLE)
              .id(0L)
              .name(node.findValue("name").asText())
              .lastName("")
              .email(node.findValue("preferred_username").asText())
              .active(true)
              .company(Company.builder().name("Clever it").build())
              .build();

      return UserPrincipal.create(user);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }
}
