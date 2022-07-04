package com.itransition.itransitioncoursework.controller;
//Sevinch Abdisattorova 06/21/2022 4:58 PM

import com.itransition.itransitioncoursework.entity.Tag;
import com.itransition.itransitioncoursework.entity.User;
import com.itransition.itransitioncoursework.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;


    @ModelAttribute("currentUser")
    public User currentUser(@AuthenticationPrincipal User currentUser) {
        return currentUser;
    }

    @GetMapping
    public String getTagForm(Model model) {
        List<Tag> tagList = tagService.getAll();
        model.addAttribute("tags", tagList);
        return "tag";
    }

    @PostMapping
    public String addTag(Tag tag, Model model) {
        boolean isAdded = tagService.addNewTag(tag, model);
        if (tag.getId()!=null)
            return isAdded ? "redirect:/tag?saved" : "redirect:/tag?exists";
        else
            return isAdded ? "redirect:/tag?edited" : "redirect:/tag?exists";
    }

    @GetMapping("/delete/{id}")
    public String deleteTag(@PathVariable UUID id,Model model){
        return tagService.deleteTag(id,model);
    }

}
