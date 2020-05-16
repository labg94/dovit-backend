package com.dovit.backend.security;

import com.dovit.backend.model.responses.ErrorResponse;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implements AuthenticationEntryPoint interface. This method is called whenever an exceptions is
 * thrown due to an unauthenticated user trying to access a resource that requires authentication.
 *
 * @author Ramón París
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

  @Override
  public void commence(
          HttpServletRequest request,
          HttpServletResponse response,
          AuthenticationException authException)
          throws IOException {
    logger.error("Responding with unauthorized error. Message - {}", authException.getMessage());
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    List<String> errorsMessages = new ArrayList<>();
    errorsMessages.add(authException.getMessage());
    ErrorResponse errorResponse =
            new ErrorResponse(new Date(), HttpServletResponse.SC_UNAUTHORIZED, errorsMessages);
    response.getOutputStream().println(new Gson().toJson(errorResponse));
  }
}
