package com.aub.backend_aub_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aub.backend_aub_shop.dto.TransactionDTO;
import com.aub.backend_aub_shop.service.TransactionService;

@Controller
@RequestMapping({"", "/transaction"})
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping(value={"","/getAllTransactions"})
    public String getAllTransaction(
        @RequestParam(name = "pageNumber", defaultValue="0") int pageNumber,
        @RequestParam(name = "pageSize", defaultValue="10") int pageSize,
        @RequestParam(name = "Username", required = false, defaultValue = "") String username,
        Model model
    )
    {
        Page<TransactionDTO> trans = transactionService.findAll(username, pageNumber, pageSize);

        model.addAttribute("transactionlist", trans.getContent());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("username", username);
        model.addAttribute("totalPages", trans.getTotalPages());

        return "transactionlog";
    }
    
    // @GetMapping("/someAction")
    // public String performAction(HttpSession session) {
    //     // Get user details from session
    //     String username = (String) session.getAttribute("username");

    //     if (username != null) {
    //         // Log the action
    //         transactionService.logAction(username, "Performed some action");
    //     } else {
    //         // Handle if user is not logged in
    //         return "redirect:/login";
    //     }

    //     return "somePage";
    // }
}
