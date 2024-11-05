package com.aub.backend_aub_shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class AdminController {
  @GetMapping("")
    public String GetAllProducts(Model m)
    {
      
        System.out.println("WOWOOWWO");
        return "product-list";
        
    }
}
