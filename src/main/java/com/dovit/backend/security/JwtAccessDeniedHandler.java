package com.dovit.backend.security;

import com.dovit.backend.model.responses.ErrorResponse;
import com.google.gson.Gson;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ramón París
 * @since 03-10-2019
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
            throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        List<String> errorsMessages = new ArrayList<>();
        errorsMessages.add(e.getMessage());
        ErrorResponse errorResponse =
                new ErrorResponse(new Date(), HttpServletResponse.SC_FORBIDDEN, errorsMessages);
        response.getOutputStream().println(new Gson().toJson(errorResponse));
    }
}
