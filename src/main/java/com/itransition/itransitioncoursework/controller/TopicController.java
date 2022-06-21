package com.itransition.itransitioncoursework.controller;
//Sevinch Abdisattorova 06/19/2022 8:31 PM

import com.itransition.itransitioncoursework.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/topic")
@RequiredArgsConstructor
public class TopicController {


    private final TopicService topicService;

    @GetMapping()
    public ResponseEntity<?> getTopics() {
       return topicService.getAllTopics();
    }

}
