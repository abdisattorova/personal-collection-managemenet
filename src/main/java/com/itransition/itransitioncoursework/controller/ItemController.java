package com.itransition.itransitioncoursework.controller;
//Sevinch Abdisattorova 06/26/2022 11:51 PM


import com.itransition.itransitioncoursework.config.security.CurrentUser;
import com.itransition.itransitioncoursework.dto.ItemDto;
import com.itransition.itransitioncoursework.entity.User;
import com.itransition.itransitioncoursework.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;


@RequestMapping("/item")
@RequiredArgsConstructor
@Controller
@Slf4j
public class ItemController {


    private final ItemService itemService;


    @GetMapping("/{collectionId}")
    public String getItemsForm(Model model, @PathVariable UUID collectionId) {
        return itemService.getItemsForm(collectionId, model);
    }


    @GetMapping("/details/{itemId}")
    public String getItemDetails(Model model, @PathVariable UUID itemId) {
        return itemService.getItemDetails(itemId, model);
    }


    @PostMapping("/add")
    public String addItemToCollection(ItemDto itemDto,
                                      MultipartHttpServletRequest file,
                                      HttpServletRequest request,
                                      RedirectAttributes redirectAttributes) throws IOException {
        return itemService.addItem(itemDto, file, request, redirectAttributes);
    }


    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/like/{itemId}")
    public String likeItem(
            @CurrentUser User user,
            @PathVariable UUID itemId) {
        return itemService.likeItem(user, itemId);
    }


    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/dislike/{itemId}")
    public String dislikeItem(
            @CurrentUser User user,
            @PathVariable UUID itemId) {
        return itemService.disLikeItem(user, itemId);
    }
}
