package com.aub.backend_aub_shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aub.backend_aub_shop.repository.DashboardRepository;

@Service
public class DashboardService {
    @Autowired private DashboardRepository dashboardRepository;

    // public DashboardModel getDashboardData() {
    //     long totalUsers = dashboardRepository.CountUsers();
    //     DashboardModel dashboardModel = new DashboardModel();
    //     dashboardModel.setTotalusers(totalUsers);
    //     return dashboardModel;
    // }
}
