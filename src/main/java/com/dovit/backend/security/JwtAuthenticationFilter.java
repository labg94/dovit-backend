package com.dovit.backend.security;

import com.dovit.backend.util.Constants;
import com.dovit.backend.util.LdapUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.dovit.backend.util.Constants.AZURE_ACCESS;
import static com.dovit.backend.util.Constants.DOVIT_ACCESS;

/**
 * To get the JWT token from the request, validate it, load the user associated with the token, and
 * pass it to Spring Security
 *
 * @author Ramón París
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired private JwtTokenProvider tokenProvider;

  @Autowired private CustomUserDetailsService customUserDetailsService;

  @Autowired private LdapUtil ldapUtil;
  @Autowired private CustomAzureUserDetails customAzureUserDetails;
  private final ObjectMapper objectMapper = new ObjectMapper();

  private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      final String jwt = getJwtFromRequest(request);
      if (StringUtils.hasText(jwt)) {
        final String tokenCreator = getTokenCreator(jwt);
        if (tokenCreator.equals(AZURE_ACCESS)) {
          final UserDetails userDetails = customAzureUserDetails.createUserDetailByAzure(jwt);
          setSecurityContext(request, userDetails);
        } else if (tokenCreator.equals(DOVIT_ACCESS)) {
          Long userId = tokenProvider.getUserIdFromJWT(jwt);
          UserDetails userDetails = customUserDetailsService.loadUserById(userId);
          setSecurityContext(request, userDetails);
        }
      }
    } catch (Exception e) {
      logger.error("Could not set user authentication in security context", e);
    }

    filterChain.doFilter(request, response);
  }

  private void setSecurityContext(HttpServletRequest request, UserDetails userDetails) {
    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  private String getJwtFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  private String getTokenCreator(String token) {
    try {
      final JsonNode isLdapUser =
          new ObjectMapper()
              .readTree(new String(Base64Utils.decodeFromString(token.split("\\.")[1])))
              .findValue("isLdapUser");
      if (isLdapUser == null) {
        return AZURE_ACCESS;
      }
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    return Constants.DOVIT_ACCESS;
  }
}
