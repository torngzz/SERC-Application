package com.aub.backend_aub_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aub.backend_aub_shop.service.DashboardService;
import com.aub.backend_aub_shop.service.UserService;

@Controller
@RequestMapping(value = {"/dashboard"})
public class DashboardController {

  @Autowired DashboardService dashboardService;
  @Autowired UserService userService;


    @GetMapping("/")
    public String showDashboard(Model model){
      int totalUsers = userService.CountTotalUser();
      model.addAttribute("totalUsers", totalUsers);
      return "/Dashboard/dashboard";
    }

    // @GetMapping("/getTotalUser")
    // public DashboardModel  getTotalUser(){
    //   return dashboardService.getDashboardData();
    // }


}
