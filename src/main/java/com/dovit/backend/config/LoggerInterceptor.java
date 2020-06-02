package com.dovit.backend.config;

import com.dovit.backend.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
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
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView)
      throws Exception {

    if (!request.getServletPath().contains("images")
        && !request.getServletPath().contains("error")) {
      log.info(
          "{} Requested {} with {} | Response HttpStatus: {}",
          this.getRequestUser(),
          request.getServletPath(),
          request.getMethod(),
          response.getStatus());
    }
    super.postHandle(request, response, handler, modelAndView);
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
