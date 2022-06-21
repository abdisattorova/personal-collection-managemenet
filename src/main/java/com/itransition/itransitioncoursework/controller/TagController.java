package com.itransition.itransitioncoursework.controller;
//Sevinch Abdisattorova 06/21/2022 4:58 PM

import com.itransition.itransitioncoursework.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.accessibility.AccessibleStreamable;

@Controller
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public String getTagForm() {
        return "tag";
    }

    @PostMapping
    public String addTag(String tag) {
        tagService.addNewTag(tag);
        // TODO: 06/21/2022 return success page 
        return "collection";
    }

//    @PutMapping
//    public AccessibleStreamable

}
