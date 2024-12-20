package com.aub.backend_aub_shop.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.aub.backend_aub_shop.model.UserModel;
import com.aub.backend_aub_shop.model.UserSessionManager;
import com.aub.backend_aub_shop.service.TransactionService;
import com.aub.backend_aub_shop.service.UserService;
import com.aub.backend_aub_shop.util.LogAction;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    // private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);
    @Autowired
    @Lazy
    private UserService userService;

    @Autowired private TransactionService transactionService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        // Retrieve UserModel from the database
        UserModel userModel = userService.findUserByUsername(username);

        if (userModel != null) {
            // Create a UserSessionManager object and store it in the session
            UserSessionManager userSessionManager = new UserSessionManager();
            userSessionManager.setUserId(userModel.getId());
            userSessionManager.setUsername(userModel.getUsername());
            HttpSession session = request.getSession();
            session.setAttribute("UserSessionManager", userSessionManager);
            transactionService.logTransaction(userModel, LogAction.LOGIN, "Success");
        } else {
            logger.error("UserModel not found for username: " + username);
            transactionService.logTransaction(userModel, LogAction.LOGIN, "Failed");
        }

        logger.info("Authentication success for user: " + username);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}