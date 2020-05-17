package com.dovit.backend.exceptions;

import com.dovit.backend.model.requests.AuthRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ramón París
 * @since 16-05-20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomBadCredentialsException extends RuntimeException {
  private AuthRequest request;

  public CustomBadCredentialsException(String msg, AuthRequest userRequest) {
    super(msg);
    this.request = userRequest;
  }
}
