package com.aub.backend_aub_shop.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aub.backend_aub_shop.model.TransactionModel;
import com.aub.backend_aub_shop.model.UserModel;
import com.aub.backend_aub_shop.service.LoginService;
import com.aub.backend_aub_shop.service.TransactionService;
import com.aub.backend_aub_shop.service.UserService;
import com.aub.backend_aub_shop.util.LogAction;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired private LoginService loginService;
    @Autowired private UserService userService;
    @Autowired private TransactionService transactionService;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    
    @GetMapping("/login")
    public String showLoginPage() {
        return "Login/login";
    }

    // @PostMapping("/loginProcess")
    // public String login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
    //     // Add your custom login logic here (e.g., authenticate the user)
    //     return "redirect:/home";
    // }

    @PostMapping("/login")
    public String loginProcess(HttpServletRequest request, Model m) {
        int status = 0;
        try {
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("username");

            // Retrieve UserModel using the username
            UserModel userModel = userService.findUserByUsername(username); // Adjust according to your service/repository

            if (userModel == null) {
                // Handle case where user is not found
                return "redirect:/users/error";
            }

            status = loginService.checkStatus(username);
            if (status == 0) {
                return "redirect:/users/error";
            } else {
                m.addAttribute("username", username);
                log.info("The username that logged into the system is : " + username);

                LogAction logAction = LogAction.LOGIN;
                TransactionModel tran = new TransactionModel();
                tran.setUsername(userModel); // Set the UserModel here
                tran.setAction(logAction);
                tran.setStatus("");
                tran.setTransanctionDate(new Date());

                return "redirect:users";
            }
        } catch (IllegalArgumentException e) {
            return "redirect:/users/error";   
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            UserModel userModel = (UserModel) session.getAttribute("user"); // Fetch UserModel from session
            if (userModel != null) {
                transactionService.logTransaction(userModel, LogAction.LOGOUT);
            }

            // Clear session and perform logout
            session.invalidate();
        }

        // Perform Spring Security logout
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login"; // Redirect to login page
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "login/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String ForgotPasswordProcess(
            @RequestParam("email") String email,
            @RequestParam(value = "newPassword", required = false) String newPassword,
            @RequestParam(value = "confirmPassword", required = false) String confirmPassword,
            Model model, RedirectAttributes redirectAttributes) {

        Optional<UserModel> userOptional = loginService.findEmail(email);

        if (userOptional.isPresent()) {
            // Check if newPassword and confirmPassword fields are provided
            if (newPassword != null && confirmPassword != null) {
                // Validate if the passwords match
                if (!newPassword.equals(confirmPassword)) {
                    model.addAttribute("errorMessage", "Passwords do not match.");
                    return "login/forgot-password";
                }
                // Update password if the passwords match
                loginService.updatePassword(email, newPassword);
                // Log success message
                System.out.println("Password update success message: " + "Password updated successfully!");
                redirectAttributes.addFlashAttribute("successMessage", "Password updated successfully!");
                return "redirect:/login/login"; // Redirect to login page with flash attribute
            }
            model.addAttribute("email", email);
            model.addAttribute("emailExists", true); // Show password fields if email exists
        } else {
            model.addAttribute("errorMessage", "Email not found.");
        }
        return "login/forgot-password";
    }
    
    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestParam("email") String email,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "Passwords do not match.");
            return "login/reset-password";
        }
        loginService.updatePassword(email, newPassword);
        model.addAttribute("email", email);
        model.addAttribute("successMessage", "Password updated successfully!");
        return "login/login"; // Redirect to login after successful reset
    }
    
    // Alert using sweetalert with no ajax
    // // @ResponseBody
    // @PostMapping("/change-password")
    // public String changePassword(@RequestParam("oldPassword") String oldPassword,
    //                             @RequestParam("newPassword") String newPassword,
    //                             @RequestParam("cfPassword") String confirmPassword,
    //                             RedirectAttributes redirectAttributes) {

    //     String username = SecurityContextHolder.getContext().getAuthentication().getName();
    //     log.info("Entering change password with oldPassword={}, newPassword={}, confirmPassword={}", oldPassword, newPassword, confirmPassword);

    //     try {
    //         loginService.changePassword(username, oldPassword, newPassword, confirmPassword);
    //         redirectAttributes.addFlashAttribute("successMessage", "Password changed successfully.");
    //     } catch (Exception e) {
    //         redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    //     }
    //     return "redirect:/users"; // Flash attributes persist in the redirect
    // }

    // @ResponseBody
    @PostMapping("/change-password")
    @ResponseBody // Add this to return JSON directly
    public Map<String, String> changePassword(
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("cfPassword") String confirmPassword) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, String> response = new HashMap<>();
        try {
            loginService.changePassword(username, oldPassword, newPassword, confirmPassword);
            response.put("status", "success");
            response.put("message", "Password changed successfully.");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }
        return response;
    }
}