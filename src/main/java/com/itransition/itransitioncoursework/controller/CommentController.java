package com.itransition.itransitioncoursework.controller;
//Sevinch Abdisattorova 07/02/2022 9:41 AM


import com.itransition.itransitioncoursework.dto.CommentDto;
import com.itransition.itransitioncoursework.entity.User;
import com.itransition.itransitioncoursework.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;


    @ModelAttribute("currentUser")
    public User currentUser(@AuthenticationPrincipal User currentUser) {
        return currentUser;
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @PostMapping("/add")
    public String addComment(
            CommentDto commentDto
    ) {
        return commentService.addComment(commentDto);
    }

    @PostMapping("/delete")
    public String deleteComment(
          @RequestParam UUID id
    ) {
        return commentService.deleteComment(id);
    }


    @PostMapping("/edit")
    public String editComment(
            CommentDto commentDto
    ) {
        return commentService.editComment(commentDto);
    }

}
