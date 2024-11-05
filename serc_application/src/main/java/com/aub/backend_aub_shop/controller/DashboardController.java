package com.aub.backend_aub_shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"dashboard"})
public class DashboardController {

  @GetMapping("/")
  public String showDashboard(){
    return "/Dashboard/dashboard";
  }
}
