package com.itransition.itransitioncoursework.controller;
//Sevinch Abdisattorova 07/11/2022 6:58 AM


import com.itransition.itransitioncoursework.dto.CollectionDto;
import com.itransition.itransitioncoursework.entity.User;
import com.itransition.itransitioncoursework.service.CollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {

    private final CollectionService collectionService;

    @ModelAttribute("currentUser")
    public User currentUser(@AuthenticationPrincipal User currentUser) {
        return currentUser;
    }

    @ModelAttribute("collection")
    public CollectionDto modelAttribute() {
        return new CollectionDto();
    }


    @GetMapping
    public String getMainPage(Model model,
                              @RequestParam(value = "text", required = false) String text) {
        return collectionService.getMainPage(model, text);
    }


}
