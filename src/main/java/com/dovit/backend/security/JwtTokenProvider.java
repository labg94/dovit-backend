package com.dovit.backend.security;

import com.dovit.backend.exceptions.BadRequestException;
import com.dovit.backend.model.requests.RegisterTokenRequest;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The following utility class will be used for generating a JWT after a user logs in successfully,
 * and validating the JWT sent in the Authorization header of the requests
 *
 * @author Ramón París
 * @since 30-07-2019
 */
@Component
public class JwtTokenProvider {

  private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

  @Value("${api.jwt.secret}")
  private String JWT_SECRET;

  @Value("${api.jwt.expirationInMs}")
  private Long JWT_EXPIRATION_IN_MS;

  @Value("${api.jwt.register.expiration}")
  private Long JWT_REGISTER_EXPIRATION;

  public String generateAuthToken(Authentication authentication) {
    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_IN_MS);

    Map<String, Object> customClaim = new HashMap<>();
    customClaim.put("isLdapUser", false);

    return Jwts.builder()
            .setClaims(customClaim)
            .setSubject(Long.toString(userPrincipal.getId()))
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
            .compact();
  }

  public String generateAuthToken(CustomLdapUserDetails userPrincipal) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_IN_MS);

    Map<String, Object> customClaim = new HashMap<>();
    customClaim.put("isLdapUser", true);

    return Jwts.builder()
            .setClaims(customClaim)
            .setSubject(userPrincipal.getUsername())
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
            .compact();
  }

  public String generateRegisterToken(RegisterTokenRequest registerTokenRequest) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + JWT_REGISTER_EXPIRATION);
    Map<String, Object> claims = new HashMap<>();
    claims.put("companyId", registerTokenRequest.getCompanyId());

    return Jwts.builder()
            .setClaims(claims)
            .setSubject(registerTokenRequest.getEmail())
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
            .compact();
  }

  public RegisterTokenRequest getRegisterRequestFromJWT(String token) {
    Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();

    return new RegisterTokenRequest(claims.getSubject(), claims.get("companyId", Long.class));
  }

  public Map<String, Object> getUserIdFromJWT(String token) {
    Claims claims = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody();

    Map<String, Object> map = new HashMap<>();
    map.put("isLdapUser", claims.get("isLdapUser"));
    map.put("subject", claims.getSubject());

    return map;
  }

  public boolean validateToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      logger.error("Invalid JWT Signature");
    } catch (MalformedJwtException e) {
      logger.error("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      logger.error("Expired JWT token");
      throw new BadRequestException("Token expiró");
    } catch (UnsupportedJwtException ex) {
      logger.error("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      logger.error("JWT claims string is empty.");
    }
    return false;
  }
}
