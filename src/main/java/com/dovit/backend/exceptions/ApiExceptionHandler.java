package com.dovit.backend.exceptions;

import com.dovit.backend.model.responses.ErrorResponse;
import com.dovit.backend.services.AuditService;
import com.dovit.backend.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 01-10-2019
 */
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private AuditService auditService;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setStatus(status.value());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        errorResponse.setErrors(errors);

        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequest(BadRequestException e) {
        List<String> errorsMessages = new ArrayList<>();
        errorsMessages.add(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(new Date(),HttpStatus.BAD_REQUEST.value(),errorsMessages);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException e) {
        List<String> errorsMessages = new ArrayList<>();
        errorsMessages.add(e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(new Date(),HttpStatus.NOT_FOUND.value(),errorsMessages);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentials(BadCredentialsException e) {
        List<String> errorsMessages = new ArrayList<>();
        errorsMessages.add("Usuario y password no coinciden");
        ErrorResponse errorResponse = new ErrorResponse(new Date(),HttpStatus.BAD_REQUEST.value(),errorsMessages);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({DataIntegrityViolationException.class })
    public ResponseEntity<?> handleConflict(DataIntegrityViolationException e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setStatus(409);
        List<String> errors = new ArrayList<>();
        errors.add(e.getMostSpecificCause().getMessage().split("Detail:")[1]);
        errorResponse.setErrors(errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({CustomAccessDeniedException.class, AccessDeniedException.class})
    public ResponseEntity<?> handleAccessDenied(Exception e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setStatus(HttpStatus.FORBIDDEN.value());
        List<String> errors = new ArrayList<>();
        errors.add(e.getMessage());
        errorResponse.setErrors(errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class, SQLException.class})
    public ResponseEntity<?> handleGeneralException(Exception e) throws AccessDeniedException {
        e.printStackTrace();
        if (e.getMessage().equals(Constants.ACCESS_DENIED)){
            throw new AccessDeniedException("Acceso denegado");
        }
        List<String> errorsMessages = new ArrayList<>();
        errorsMessages.add("Ocurrió un error interno en el servidor. Si el problema persiste contacte al equipo técnico");
        ErrorResponse errorResponse = new ErrorResponse(new Date(),HttpStatus.INTERNAL_SERVER_ERROR.value(),errorsMessages);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
