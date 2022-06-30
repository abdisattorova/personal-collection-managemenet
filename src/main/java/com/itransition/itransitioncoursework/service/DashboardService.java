package com.itransition.itransitioncoursework.service;
//Sevinch Abdisattorova 06/30/2022 11:48 AM


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;


@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserService userService;
    private final CollectionService collectionService;


    public String getAdminDashboardStatistics(Model model) {
        Integer adminsCount = userService.getAdminsCount();
        Integer usersCount = userService.getUsersCount();
        Integer collectionsCount = collectionService.getCountOfAllCollections();
        model.addAttribute("adminsCount", adminsCount);
        model.addAttribute("collectionsCount", collectionsCount);
        model.addAttribute("usersCount", usersCount);
        return "dashboard";
    }
}
