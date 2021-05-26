package com.storactive.stg.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler
        implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException {

        response.setStatus(HttpStatus.FORBIDDEN.value());

        String redirectUrl;
        if (exception.getMessage().contains("Bad credentials"))
            redirectUrl = "/login?bad_credentials";
        else if (exception.getMessage().contains("account is disabled"))
            redirectUrl = "/login?disabled";
        else
            redirectUrl = "/login?error=" + exception.getMessage();

        response.sendRedirect(redirectUrl);
    }
}
