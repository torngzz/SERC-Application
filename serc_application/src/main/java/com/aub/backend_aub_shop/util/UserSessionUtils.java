package com.aub.backend_aub_shop.util;

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
        // HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        return (UserSessionManager) session.getAttribute("UserSessionManager");
    }

    public static Long getUserId(HttpSession session) {
        return getUserSessionManager(session).getUserId();
    }

    public static String getUsername(HttpSession session) {
        return getUserSessionManager(session).getUsername();
    }
}

