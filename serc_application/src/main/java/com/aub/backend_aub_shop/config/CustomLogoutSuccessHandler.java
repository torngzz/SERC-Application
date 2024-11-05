package com.aub.backend_aub_shop.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);

    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, 
                                HttpServletResponse response, 
                                Authentication authentication)
                                throws IOException, ServletException {
        if (authentication != null) {
            logger.info("Logging out user: {}", authentication.getName());
        }

        // Invalidate all sessions associated with the user
        sessionRegistry.getAllPrincipals().forEach(principal -> {
            if (principal.equals(authentication.getPrincipal())) {
                sessionRegistry.getAllSessions(principal, false).forEach(session -> {
                    // Get the session ID and remove the session information
                    String sessionId = session.getSessionId();
                    sessionRegistry.removeSessionInformation(sessionId);
                });
            }
        });

        request.getSession().invalidate(); // Explicitly invalidate the session
        response.sendRedirect("/login?logout");
    }
}