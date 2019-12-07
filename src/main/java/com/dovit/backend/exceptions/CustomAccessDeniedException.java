package com.dovit.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Ramón París
 * @since 03-10-2019
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class CustomAccessDeniedException extends RuntimeException {

    public CustomAccessDeniedException(String message) {
        super(message);
    }

    public CustomAccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
