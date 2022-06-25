package com.itransition.itransitioncoursework.controller;
//Sevinch Abdisattorova 06/19/2022 8:31 PM

import com.itransition.itransitioncoursework.entity.User;
import com.itransition.itransitioncoursework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;


    @GetMapping
    public String getTopicForm(Model model,
                               @RequestParam(required = false, defaultValue = "1") Integer page) {
        Page<User> users = userService.getUsersByPage(page);
        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("pages", users.getTotalPages());
        return "users";
    }

//
//    @PostMapping
//    public String addTopic(Topic topic,
//                           Model model,
//                           @RequestParam Integer currentPage) {
//        if (topic.getName().equals("")){
//            model.addAttribute("success",false);
//            model.addAttribute("message","Topic can't be empty");
//            return "redirect:/topic?empty";
//        }
//        userService.addNewTopic(topic, model, currentPage);
//        return "redirect:/topic";
//    }
//
//
//    @GetMapping("/delete/{id}/currentPage/{page}")
//    public String deleteTopic(@PathVariable UUID id,
//                              Model model,
//                              @PathVariable Integer page) {
//        userService.deleteTopic(id, model, page);
//        return  "redirect:/topic";
//    }
//
//    @GetMapping("/archive/{id}/currentPage/{page}")
//    public String archiveTopic(@PathVariable UUID id,
//                               Model model,
//                               @PathVariable Integer page) {
//        userService.archive(id, model, page);
//        return  "redirect:/topic";
//    }
//
//    @GetMapping("/unarchive/{id}/currentPage/{page}")
//    public String unarchiveTopic(@PathVariable UUID id,
//                                 Model model,
//                                 @PathVariable Integer page) {
//        userService.unarchive(id, model, page);
//        return  "redirect:/topic";
//    }

}
