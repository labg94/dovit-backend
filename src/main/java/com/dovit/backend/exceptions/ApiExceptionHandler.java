package com.dovit.backend.exceptions;

import com.dovit.backend.model.responses.ErrorResponse;
import com.dovit.backend.services.AuditService;
import com.dovit.backend.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 01-10-2019
 */
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler implements RequestBodyAdvice {

    @Autowired
    private AuditService auditService;

    private static final ThreadLocal<ApiExceptionHandler> requestInfoThreadLocal = new ThreadLocal<>();

    private String method;
    private Object body;
    private String queryString;
    private String ip;
    private String user;
    private String referrer;
    private String url;

    public static ApiExceptionHandler get() {
        ApiExceptionHandler requestInfo = requestInfoThreadLocal.get();
        if (requestInfo == null) {
            requestInfo = new ApiExceptionHandler();
            requestInfoThreadLocal.set(requestInfo);
        }
        return requestInfo;
    }

    public Map<String,Object> asMap() {
        Map<String,Object> map = new HashMap<>();
        map.put("method", this.method);
        map.put("url", this.url);
        map.put("queryParams", this.queryString);
        map.put("body", this.body);
        map.put("ip", this.ip);
        map.put("referrer", this.referrer);
        map.put("user", this.user);
        return map;
    }

    private void setInfoFromRequest(HttpServletRequest request) {
        this.method = request.getMethod();
        this.queryString = request.getQueryString();
        this.ip = request.getRemoteAddr();
        this.referrer = request.getRemoteHost();
        this.url = request.getRequestURI();
        if (request.getUserPrincipal() != null) {
            this.user = request.getUserPrincipal().getName();
        }
    }

    public void setBody(Object body) {
        this.body = body;
    }

    private static void setInfoFrom(HttpServletRequest request) {
        ApiExceptionHandler requestInfo = requestInfoThreadLocal.get();
        if (requestInfo == null) {
            requestInfo = new ApiExceptionHandler();
        }
        requestInfo.setInfoFromRequest(request);
        requestInfoThreadLocal.set(requestInfo);
    }

    private static void clear() {
        requestInfoThreadLocal.remove();
    }

    private static void setBodyInThreadLocal(Object body) {
        ApiExceptionHandler requestInfo = get();
        requestInfo.setBody(body);
        setApiExceptionHandler(requestInfo);
    }

    private static void setApiExceptionHandler(ApiExceptionHandler requestInfo) {
        requestInfoThreadLocal.set(requestInfo);
    }

    // Implementation of RequestBodyAdvice to capture the request body and be able to add it to the report in case of an error

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        ApiExceptionHandler.setBodyInThreadLocal(body);
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

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
    public ResponseEntity<?> handleBadCredentials(BadCredentialsException e, WebRequest request) {
        List<String> errorsMessages = new ArrayList<>();
        errorsMessages.add("Usuario y password no coinciden");
        ErrorResponse errorResponse = new ErrorResponse(new Date(),HttpStatus.BAD_REQUEST.value(),errorsMessages);

        ApiExceptionHandler apiExceptionHandler = get();
        auditService.registerAudit(apiExceptionHandler.body, "INICIO DE SESIÓN", "ERROR", null);

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
