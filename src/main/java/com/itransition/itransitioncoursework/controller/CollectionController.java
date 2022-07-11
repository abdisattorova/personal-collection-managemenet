package com.itransition.itransitioncoursework.controller;
//Sevinch Abdisattorova 06/18/2022 9:36 PM

import com.itransition.itransitioncoursework.config.security.CurrentUser;
import com.itransition.itransitioncoursework.dto.CollectionDto;
import com.itransition.itransitioncoursework.entity.User;
import com.itransition.itransitioncoursework.service.CollectionService;
import com.itransition.itransitioncoursework.service.TopicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.UUID;


@Controller
@RequestMapping("/collection")
@RequiredArgsConstructor
@Slf4j
public class CollectionController {

    private final TopicService topicService;
    private final CollectionService collectionService;


    @ModelAttribute("currentUser")
    public User currentUser(@AuthenticationPrincipal User currentUser) {
        return currentUser;
    }

    @ModelAttribute("collection")
    public CollectionDto modelAttribute() {
        return new CollectionDto();
    }


    @GetMapping("/collections")
    public String getCollections(Model model) {
        return collectionService.getAllCollections(model);
    }

    @GetMapping("/my-collections")
    public String getMyCollections(Model model, @CurrentUser User user) {
        return collectionService.getMyCollections(model, user);
    }


    @GetMapping("/details/{collectionId}")
    public String getDetailsOfCollection(@PathVariable UUID collectionId, Model model) {
        return collectionService.getDetailsOfCollection(collectionId, model);
    }


    @GetMapping
    public String getCollectionForm(Model model) {
        model.addAttribute("collection", new CollectionDto());
        model.addAttribute("topics", topicService.getAllTopics());
        return "collection";
    }


    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String addCollection(
            @CurrentUser User user,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @Valid
            @RequestPart CollectionDto collection,
            BindingResult bindingResult

    ) throws IOException {
        if (bindingResult.hasErrors()) {
            return "redirect:/collection?error";
        }
//        String contentType = image.getContentType();
//        if (!contentType.endsWith("jpeg")
//                || !contentType.endsWith("jpg")
//                || !contentType.endsWith("png")) {
//            FieldError fieldError = new FieldError("collection", "image", "You can only choose images");
//            bindingResult.addError(fieldError);
//            return "collection";
//        }
        System.out.println(collection);
        System.out.println(user);
        collectionService.saveCollection(collection, image);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteCollection(@RequestParam UUID collectionId, RedirectAttributes attributes) {
        return collectionService.deleteCollection(collectionId, attributes);
    }

}
