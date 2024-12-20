package com.aub.backend_aub_shop.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aub.backend_aub_shop.dto.UserDTO;
import com.aub.backend_aub_shop.model.UserModel;
import com.aub.backend_aub_shop.service.TransactionService;
import com.aub.backend_aub_shop.service.UserService;
import com.aub.backend_aub_shop.util.LogAction;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = {"", "/users"})
//set tr role Admin
// @PreAuthorize("hasRole('Admin')")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping(value = {"/userList"})
    public String getAllUser(
        @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
        @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
        @RequestParam(name = "username", required = false, defaultValue = "") String username,
        Model model
    ) {
        Page<UserDTO> users = userService.findAll(username, pageNumber, pageSize);

        model.addAttribute("users", users.getContent());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("username", username);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("currentPage", pageNumber);

        return "UserManagement/user-list";
    }    
    
    @GetMapping("/withoutpagination")
    public String getAllUsers(Model m){
        List<UserModel> users = userService.findAll(); 
        m.addAttribute("users", users);
        return "UserManagement/user-list";
    }

    @GetMapping("/addUser")
    public String addUser(Model m)
    {
        m.addAttribute("user", new UserModel());
        return "UserManagement/add-user";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") UserModel userModel, HttpServletRequest httpRequest, Model m) 
    {
        try{
            userService.create(httpRequest, userModel);
            transactionService.logTransaction(userModel, LogAction.CREATE, "Success");
            return "redirect:/users/userList";
        }
        catch (IllegalArgumentException e) {
            m.addAttribute("usernameError", e.getMessage());
            transactionService.logTransaction(userModel, LogAction.CREATE, "Failed");
            return "UserManagement/add-user";  // Name of your Thymeleaf template file without the ".html" extension
        }
    }

    // @PostMapping("/save")
    // public String saveUser(@ModelAttribute("user") UserModel userModel, HttpServletRequest httpRequest, Model m) {
    //     boolean hasError = false;

    //     // Validate the username
    //     try {
    //         userService.validateUsername(userModel.getUsername());
    //     } catch (IllegalArgumentException e) {
    //         m.addAttribute("usernameError", e.getMessage());
    //         hasError = true;
    //     }

    //     // Validate the email
    //     try {
    //         userService.validateEmail(userModel.getEmail());
    //     } catch (IllegalArgumentException e) {
    //         m.addAttribute("emailError", e.getMessage());
    //         hasError = true;
    //     }

    //     // Validate the phone number
    //     try {
    //         userService.validatePhone(userModel.getPhone());
    //     } catch (IllegalArgumentException e) {
    //         m.addAttribute("phoneError", e.getMessage());
    //         hasError = true;
    //     }

    //     // If any errors were found, return to the form page
    //     if (hasError) {
    //         return "UserManagement/add-user";  // Name of your Thymeleaf template file without the ".html" extension
    //     }

    //     // Proceed with user creation if no errors
    //     try {
    //         userService.create(httpRequest, userModel);
    //         return "redirect:/users";
    //     } catch (Exception e) {
    //         // // Handle any other unexpected exceptions
    //         m.addAttribute("generalError", "An unexpected error occurred. Please try again.");
    //         return "UserManagement/add-user";  // Name of your Thymeleaf template file without the ".html" extension

    //     }
    // }

    @GetMapping("/edit/{id}")
    public String UpdateById(@PathVariable("id") UUID id, Model m){
        Optional<UserModel> users = userService.findById(id);
        m.addAttribute("user", users.orElse(new UserModel()));
        return "UserManagement/edit-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") UUID id, @ModelAttribute("user") UserModel users, Model m, HttpServletRequest httpRequest) {
        try {
            UserModel updateUser = userService.update(users, id, httpRequest);
            transactionService.logTransaction(users, LogAction.UPDATE, "Success");

            m.addAttribute("user", updateUser);
            return "redirect:/users/userList";
        } catch (IllegalArgumentException e) {
            // Handle specific exceptions like IllegalArgumentException
            m.addAttribute("error", e.getMessage());
            transactionService.logTransaction(users, LogAction.UPDATE, "Failed");
            return "redirect:/users/error";
        } catch (Exception e) {
            // Handle any other unexpected exceptions
            m.addAttribute("error", "An unexpected error occurred.");
            transactionService.logTransaction(users, LogAction.UPDATE, "Failed");
            return "redirect:/users/error";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable UUID id) {
        userService.deleteById(id);
        Logger logger = LoggerFactory.getLogger(UserService.class);
        logger.info("User with Id" + id);
        return "redirect:/users/userList"; 
    }

    @GetMapping("/disable/{id}")
    public String disableUser(@PathVariable Long id){
        // userService.disableById(id);
        return "redirect:/users";
    }

    @GetMapping("/error")
    public String errorPage(){
        return "error/page-404";
    }

}
