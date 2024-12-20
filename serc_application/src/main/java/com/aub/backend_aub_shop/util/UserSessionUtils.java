package com.aub.backend_aub_shop.util;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.aub.backend_aub_shop.model.UserSessionManager;

import jakarta.servlet.http.HttpSession;

@Component
public class UserSessionUtils {
    public void setAttribute(String attrName, Object value, HttpSession session) 
    {
        session.setAttribute(attrName, value);
    }

    public static Object getAttribute(String attrName, HttpSession session) 
    {
        return session.getAttribute(attrName);
    }

    public static UserSessionManager getUserSessionManager(HttpSession session) {
        if (session == null) {
            return null; // Return null if the session is null
        }
        return (UserSessionManager) session.getAttribute("UserSessionManager");
    }

    public static UUID getUserId(HttpSession session) {
        UserSessionManager userSessionManager = getUserSessionManager(session);
        if (userSessionManager == null) {
            return null; // Return null if UserSessionManager is not in the session
        }
        return userSessionManager.getUserId();
    }


    // public static UserModel getUserIdBy(HttpSession session) {
    //     return getUserSessionManager(session).getUserId();
    // }
    public static String getUsername(HttpSession session) {
        UserSessionManager userSessionManager = getUserSessionManager(session);
        if (userSessionManager == null) {
            return null; // Return null if UserSessionManager is not in the session
        }
        return userSessionManager.getUsername();
    }

}

