package com.dovit.backend.config;

import com.dovit.backend.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ramón París
 * @since 02-06-20
 */
@Slf4j
public class LoggerInterceptor extends HandlerInterceptorAdapter {

  @Override
  public void afterCompletion(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
      throws Exception {
    if (!request.getServletPath().contains("images")
        && !request.getServletPath().contains("error")) {
      final String message =
          String.format(
              "%s requested %s with %s | Response HttpStatus: %s",
              this.getRequestUser(),
              request.getServletPath(),
              request.getMethod(),
              response.getStatus());

      if (response.getStatus() >= 500) {
        log.error(message);
      } else if (response.getStatus() >= 300) {
        log.warn(message);
      } else {
        log.info(message);
      }
    }
    super.afterCompletion(request, response, handler, ex);
  }

  private String getRequestUser() {
    try {
      UserPrincipal loggedUser =
          (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      return String.format(
          "%s %s(%s)", loggedUser.getName(), loggedUser.getLastName(), loggedUser.getId());
    } catch (Exception e) {
      return "";
    }
  }
}
