package com.serc.serc_application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping({""})
public class UserController {

    @GetMapping("/log")
    public String showLogin() {

        return "login"; // This should match the path to your HTML file
    }

    @PostMapping("/login_process")
    public String loginProcess(HttpServletRequest request, Model m) {
        return "redirect:main";
        // try {
        //     return "redirect:/main"; // Ensure you have a mapping for /users
        // } catch (IllegalArgumentException e) {
        //     return "redirect:/users/error"; // Ensure this mapping exists
        // }
    }
}