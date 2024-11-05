package com.aub.backend_aub_shop.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.aub.backend_aub_shop.model.UserModel;
import com.aub.backend_aub_shop.model.UserSessionManager;
import com.aub.backend_aub_shop.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    private UserService userService;

    @Autowired
    public void setUserService(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        try {
            HttpSession session = request.getSession();
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            LOGGER.info("User {} successfully authenticated.", userDetails.getUsername());

            // Store username in session
            session.setAttribute("username", userDetails.getUsername());

            // Get user data from the database
            UserModel user = userService.findUserByUsername(userDetails.getUsername());

            // Set user data in session
            session.setAttribute("UserSessionManager", new UserSessionManager(user.getId(), user.getUsername()));

            // Extract user roles and set them in session
            List<String> roles = extractUserRoles(authentication);
            session.setAttribute("roles", roles);

            // Redirect to the homepage or desired location
            response.sendRedirect(request.getContextPath() + "/");
        } catch (UsernameNotFoundException e) {
            LOGGER.error("User not found: {}", e.getMessage(), e);
            response.sendRedirect(request.getContextPath() + "/error");
        } catch (Exception e) {
            LOGGER.error("An unexpected error occurred during authentication.", e);
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }

    private List<String> extractUserRoles(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<>();

        for (GrantedAuthority authority : authorities) {
            LOGGER.info("Authorities: {}", authority.getAuthority());
            roles.add(authority.getAuthority());
        }
        return roles;
    }
}
