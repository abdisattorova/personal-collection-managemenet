package com.itransition.itransitioncoursework.controller;
//Sevinch Abdisattorova 06/18/2022 9:36 PM

import com.itransition.itransitioncoursework.dto.CollectionDto;
import com.itransition.itransitioncoursework.service.CollectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;


@Controller
@RequestMapping("/collection")
@RequiredArgsConstructor
@Slf4j
public class CollectionController {


    private final CollectionService collectionService;


    @ModelAttribute("collection")
    public CollectionDto modelAttribute() {
        return new CollectionDto();
    }

    @GetMapping
    public String getCollectionForm(Model model) {
        model.addAttribute("collection", new CollectionDto());
        return "collection";
    }


    @PermitAll
    @PostMapping(
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public String addCollection(
            @RequestPart("image") MultipartFile image,
            @Valid
            @RequestPart CollectionDto collection,
            BindingResult bindingResult

    ) {

//        if (bindingResult.hasErrors()) {
//            return "redirect:/collection?imageError";
//        }


//        String contentType = image.getContentType();
//        if (!contentType.endsWith("jpeg")
//                || !contentType.endsWith("jpg")
//                || !contentType.endsWith("png")) {
//            FieldError fieldError = new FieldError("collection", "image", "You can only choose images");
//            bindingResult.addError(fieldError);
//            return "collection";
//        }
//
//        System.out.println(collection);


        collectionService.saveCollection(collection,image);
        return "collection";
    }
}
