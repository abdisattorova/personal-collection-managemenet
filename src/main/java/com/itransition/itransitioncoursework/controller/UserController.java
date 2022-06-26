package com.itransition.itransitioncoursework.controller;
//Sevinch Abdisattorova 06/19/2022 8:31 PM

import com.itransition.itransitioncoursework.entity.User;
import com.itransition.itransitioncoursework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;


    @GetMapping
    public String getUsersPage(Model model,
                               @RequestParam(required = false, defaultValue = "1") Integer page) {
        Page<User> users = userService.getUsersByPage(page);
        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("pages", users.getTotalPages());
        return "users";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam UUID id,
                             RedirectAttributes model,
                             @RequestParam Integer page) {
        return userService.deleteUser(id, model, page);
    }

    @PostMapping("/block")
    public String blockUser(@RequestParam UUID id,
                            @RequestParam Integer page,
                            RedirectAttributes model) {
        return userService.blockUser(id, model, page);

    }

    @PostMapping("/unblock")
    public String unblockUser(@RequestParam UUID id,
                              RedirectAttributes model,
                              @RequestParam Integer page) {
        return userService.unblockUser(id, model, page);
    }

    @PostMapping("/promote")
    public String promoteUserToAdmin(@RequestParam UUID id,
                              RedirectAttributes model,
                              @RequestParam Integer page) {
        return userService.promoteUserToAdmin(id, model, page);
    }

    @PostMapping("/remove")
    public String removeUserFromAdmin(@RequestParam UUID id,
                                     RedirectAttributes model,
                                     @RequestParam Integer page) {
        return userService.removeUserFromAdmin(id, model, page);
    }
}
