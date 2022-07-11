package com.itransition.itransitioncoursework.controller;
//Sevinch Abdisattorova 07/11/2022 8:55 AM


import com.itransition.itransitioncoursework.entity.User;
import com.itransition.itransitioncoursework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {


    private final UserService userService;

    @PostMapping
    public String editProfile(User user, RedirectAttributes model) {
        return userService.editUser(user, model);
    }

    @GetMapping("/delete/{id}")
    public String deleteProfile(@PathVariable UUID id, RedirectAttributes attributes) {
        return userService.deleteProfile(id, attributes);
    }


}
