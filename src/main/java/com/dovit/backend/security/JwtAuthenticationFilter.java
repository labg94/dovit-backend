package com.dovit.backend.security;

import com.dovit.backend.domain.Role;
import com.dovit.backend.exceptions.CustomAccessDeniedException;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.util.LdapUtil;
import com.dovit.backend.util.RoleName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Map;

/**
 * To get the JWT token from the request, validate it, load the user associated with the token, and pass it to Spring Security
 *
 * @author Ramón París
 */

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private LdapUtil ldapUtil;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Map<String, Object> translatedToken = tokenProvider.getUserIdFromJWT(jwt);
                boolean isLdapUser = ((boolean) translatedToken.getOrDefault("isLdapUser", false));

                UserDetails userDetails = null;
                if (!isLdapUser) {
                    Long userId = (Long.parseLong(translatedToken.get("subject").toString()));
                    userDetails = customUserDetailsService.loadUserById(userId);
                } else {
                    String username = translatedToken.get("subject").toString();
                    userDetails = ldapUtil.findDataByUsername(username);
                }

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);


            }
        } catch (Exception e) {
            logger.error("Could not set user authentication in security context", e);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public static Boolean canActOnCompany(Long companyId) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String roleName = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).findFirst().orElseThrow(() -> new ResourceNotFoundException("Role", "name", ""));
        if (RoleName.ROLE_ADMIN.name().equals(roleName))
            return true;

        if (companyId.equals(userPrincipal.getCompanyId())) {
            return true;
        } else {
            throw new CustomAccessDeniedException("Access denied");
        }

    }
}
