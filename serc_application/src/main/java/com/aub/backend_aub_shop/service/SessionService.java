package com.aub.backend_aub_shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SessionRegistry sessionRegistry;

    public void expireUserSessions(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        List<SessionInformation> sessions = sessionRegistry.getAllSessions(userDetails, false);
        for (SessionInformation session : sessions) {
            session.expireNow();
        }
    }
}
