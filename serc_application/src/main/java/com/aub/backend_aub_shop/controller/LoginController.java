package com.aub.backend_aub_shop.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aub.backend_aub_shop.service.LoginService;
import com.aub.backend_aub_shop.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired private LoginService loginService;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    
    @GetMapping("/login")
    public String showLoginPage() {
        return "Login/login";
    }

    @PostMapping("/loging")
    public String loginProcess(HttpServletRequest request, Model m){
        int status = 0;
        try{
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("username");
            status = loginService.checkStatus(username);
            if(status == 0){
                return "redirect:/users/error";
            }
            else{
                m.addAttribute("username", username);
                log.info("The username that logged into the system is : " + username);
                return "redirect:users";
            }
        }
        catch(IllegalArgumentException e){
            return "redirect:/users/error";   
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login"; // Redirect to login page after logout
    }
    
    // @ResponseBody
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                            @RequestParam("newPassword") String newPassword,
                                            @RequestParam("cfPassword") String confirmPassword) {
        Map<String, Object> response = new HashMap<>();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Entering changePassword method with parameters: oldPassword={}, newPassword={}, confirmPassword={}", oldPassword, newPassword, confirmPassword);
        try {
            loginService.changePassword(username, oldPassword, newPassword, confirmPassword);
            response.put("status", "success");
            response.put("message", "Password changed successfully.");
        } catch (IllegalArgumentException e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }
        return "redirect:/users"; // Return JSON response
    }
}