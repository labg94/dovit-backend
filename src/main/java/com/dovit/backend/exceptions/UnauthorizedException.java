package com.dovit.backend.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Ramón París
 * @since 22-06-20
 */
@EqualsAndHashCode(callSuper = true)
@ResponseStatus(HttpStatus.UNAUTHORIZED)
@Data
public class UnauthorizedException extends RuntimeException {

  private String fullName;
  private String email;

  public UnauthorizedException(String message, String fullName, String email) {
    super(message);
    this.fullName = fullName;
    this.email = email;
  }
}
