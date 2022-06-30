package com.itransition.itransitioncoursework.controller;
//Sevinch Abdisattorova 06/30/2022 11:45 AM

import com.itransition.itransitioncoursework.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/dashboard")
public class DashboardController {


    private final DashboardService dashboardService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String getAdminDashboard(Model model) {
        return dashboardService.getAdminDashboardStatistics(model);
    }

}
