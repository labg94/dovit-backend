package com.dovit.backend.exceptions;

import com.dovit.backend.payloads.responses.ErrorResponse;
import com.dovit.backend.services.AuditService;
import com.dovit.backend.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 01-10-2019
 */
@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApiExceptionHandler {

  private final AuditService auditService;

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<?> handleBadRequest(BadRequestException e) {
    log.error(e.getMessage());
    List<String> errorsMessages = new ArrayList<>();
    errorsMessages.add(e.getMessage());
    ErrorResponse errorResponse =
        new ErrorResponse(new Date(), HttpStatus.BAD_REQUEST.value(), errorsMessages);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<?> handleNotFound(ResourceNotFoundException e) {
    List<String> errorsMessages = new ArrayList<>();
    errorsMessages.add(e.getMessage());
    ErrorResponse errorResponse =
        new ErrorResponse(new Date(), HttpStatus.NOT_FOUND.value(), errorsMessages);
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(CustomBadCredentialsException.class)
  public ResponseEntity<?> handleBadCredentials(CustomBadCredentialsException e) {
    List<String> errorsMessages = Collections.singletonList(e.getMessage());
    ErrorResponse errorResponse =
        new ErrorResponse(new Date(), HttpStatus.BAD_REQUEST.value(), errorsMessages);

    //    ApiExceptionHandler apiExceptionHandler = get();
    e.getRequest().setPassword(null);
    auditService.registerAudit(e.getRequest(), "Login", Constants.AUDIT_STATUS_NOK, null);

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler({DataIntegrityViolationException.class})
  public ResponseEntity<?> handleConflict(DataIntegrityViolationException e) {
    List<String> errors = new ArrayList<>();
    errors.add(e.getMostSpecificCause().getMessage().split("Detail:")[1]);
    ErrorResponse errorResponse = new ErrorResponse(new Date(), 409, errors);
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler({EntityNotFoundException.class, JpaObjectRetrievalFailureException.class})
  public ResponseEntity<?> handleEntityNotFound(JpaObjectRetrievalFailureException e) {
    List<String> errors = new ArrayList<>();
    errors.add("Unable to find " + e.getLocalizedMessage().split("com.dovit.backend.domain.")[2]);
    ErrorResponse errorResponse = new ErrorResponse(new Date(), 409, errors);
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ExceptionHandler({CustomAccessDeniedException.class, AccessDeniedException.class})
  public ResponseEntity<?> handleAccessDenied(Exception e) {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setTimestamp(new Date());
    errorResponse.setStatus(HttpStatus.FORBIDDEN.value());
    List<String> errors = new ArrayList<>();
    errors.add(e.getMessage());
    errorResponse.setErrors(errors);
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({MethodArgumentTypeMismatchException.class})
  public ResponseEntity<?> handleWrongArgument(MethodArgumentTypeMismatchException e) {
    List<String> errorsMessages =
        Collections.singletonList(
            "Param " + e.getName() + " with value " + e.getValue() + " is not in correct format");

    ErrorResponse errorResponse =
        new ErrorResponse(new Date(), HttpStatus.BAD_REQUEST.value(), errorsMessages);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity<?> handleNotValidArgument(MethodArgumentNotValidException e) {
    List<String> errorsMessages =
        e.getBindingResult().getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList());

    ErrorResponse errorResponse =
        new ErrorResponse(new Date(), HttpStatus.BAD_REQUEST.value(), errorsMessages);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({HttpMessageNotReadableException.class})
  public ResponseEntity<?> handleMessageNotReadable(HttpMessageNotReadableException e) {
    log.error("Request error", e);
    List<String> errorsMessages = Collections.singletonList("Request error");

    ErrorResponse errorResponse =
        new ErrorResponse(new Date(), HttpStatus.BAD_REQUEST.value(), errorsMessages);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
  public ResponseEntity<?> handleMethodNotAllowed(HttpRequestMethodNotSupportedException e) {
    List<String> errorsMessages =
        Collections.singletonList(e.getMethod() + " method not allowed for this endpoint");

    ErrorResponse errorResponse =
        new ErrorResponse(new Date(), HttpStatus.METHOD_NOT_ALLOWED.value(), errorsMessages);
    return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler({Exception.class, SQLException.class})
  public ResponseEntity<?> handleGeneralException(Exception e) throws AccessDeniedException {
    e.printStackTrace();
    if (e.getMessage().equals(Constants.ACCESS_DENIED)) {
      throw new AccessDeniedException("Acceso denegado");
    }
    List<String> errorsMessages = new ArrayList<>();
    errorsMessages.add(
        "Ocurrió un error interno en el servidor. Si el problema persiste contacte al equipo técnico");
    ErrorResponse errorResponse =
        new ErrorResponse(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), errorsMessages);
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
