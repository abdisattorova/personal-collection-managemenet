package com.itransition.itransitioncoursework.controller;
//Sevinch Abdisattorova 06/26/2022 11:51 PM


import com.itransition.itransitioncoursework.config.security.CurrentUser;
import com.itransition.itransitioncoursework.dto.ItemDto;
import com.itransition.itransitioncoursework.entity.User;
import com.itransition.itransitioncoursework.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @ModelAttribute("currentUser")
    public User currentUser(@AuthenticationPrincipal User currentUser) {
        return currentUser;
    }


    @GetMapping("/{collectionId}")
    public String getItemsForm(Model model, @PathVariable UUID collectionId) {
        return itemService.getItemsForm(collectionId, model);
    }

    @GetMapping("/edit-form")
    public String getItemsEditForm(Model model, @RequestParam UUID itemId) {
        return itemService.getEditForm(itemId, model);
    }


    @GetMapping("/details/{itemId}")
    public String getItemDetails(Model model, @PathVariable UUID itemId) {
        return itemService.getItemDetails(itemId, model);
    }


    @GetMapping("/all")
    public String getAllItems(Model model) {
        return itemService.getAllItems(model);
    }


    @PostMapping("/add")
    public String addItemToCollection(ItemDto itemDto,
                                      MultipartHttpServletRequest file,
                                      HttpServletRequest request,
                                      RedirectAttributes redirectAttributes) throws IOException {
        if (itemDto.getTagIds() == null || itemDto.getTagIds().size() == 0) {
            return "redirect:/item/" + itemDto.getCollectionId() + "?tagError";
        }
        return itemService.addItem(itemDto, file, request, redirectAttributes);
    }


    @PostMapping("/delete")
    public String deleteItem(@RequestParam UUID id, @RequestParam UUID collectionId, RedirectAttributes model) {
        return itemService.deleteItem(id, collectionId, model);
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
