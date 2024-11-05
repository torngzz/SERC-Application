package com.aub.backend_aub_shop.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomeFailerHandler implements AuthenticationFailureHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomeFailerHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        
        // Log the failure details
        LOGGER.error("Authentication failed: {}", exception.getMessage());

        // Add error message to request or session
        request.getSession().setAttribute("errorMessage", "Authentication failed. Please check your username and password.");

        // Redirect to login page with error parameter
        response.sendRedirect(request.getContextPath() + "/login?error=true");
    }
}
