package com.serc.serc_application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; // Ensure this import is correct

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {

@GetMapping("/login")
public String showLogin() {
    return "login"; // This should match the path to your HTML file
}

@PostMapping("/loging")
public String loginProcess(HttpServletRequest request, Model m) {
    try {
        return "redirect:/users"; // Ensure you have a mapping for /users
    } catch (IllegalArgumentException e) {
        return "redirect:/users/error"; // Ensure this mapping exists
    }
}
}