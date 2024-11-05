package com.aub.backend_aub_shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@Component
public class CustomSessionListener implements HttpSessionListener {

    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        // Not needed for this scenario
    }

    @EventListener
    public void onSessionDestroyed(HttpSessionEvent event) {
        // Get the user associated with the session
        Object user = event.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        if (user != null) {
            // Invalidate all sessions associated with the user
            sessionRegistry.getAllPrincipals().forEach(principal -> {
                if (principal.equals(user)) {
                    sessionRegistry.getAllSessions(principal, false).forEach(session -> {
                        // Get the session ID and remove the session information
                        String sessionId = session.getSessionId();
                        sessionRegistry.removeSessionInformation(sessionId);
                    });
                }
            });
        }
    }
}