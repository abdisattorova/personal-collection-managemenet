package com.itransition.itransitioncoursework.controller;
//Sevinch Abdisattorova 06/19/2022 8:31 PM

import com.itransition.itransitioncoursework.entity.Topic;
import com.itransition.itransitioncoursework.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicController {


    private final TopicService topicService;


    @GetMapping
    public String getTopicForm(Model model,
                               @RequestParam(required = false, defaultValue = "1") Integer page) {
        Page<Topic> topics = topicService.getTopicsByPage(page);
        model.addAttribute("topics", topics);
        model.addAttribute("currentPage", page);
        model.addAttribute("pages", topics.getTotalPages());
        return  "redirect:/topic";
    }


    @PostMapping
    public String addTopic(Topic topic,
                           Model model,
                           @RequestParam Integer currentPage) {
        if (topic.getName().equals("")){
            model.addAttribute("success",false);
            model.addAttribute("message","Topic can't be empty");
            return "redirect:/topic?empty";
        }
        topicService.addNewTopic(topic, model, currentPage);
        return "redirect:/topic";
    }


    @GetMapping("/delete/{id}/currentPage/{page}")
    public String deleteTopic(@PathVariable UUID id,
                              Model model,
                              @PathVariable Integer page) {
        topicService.deleteTopic(id, model, page);
        return  "redirect:/topic";
    }

    @GetMapping("/archive/{id}/currentPage/{page}")
    public String archiveTopic(@PathVariable UUID id,
                               Model model,
                               @PathVariable Integer page) {
        topicService.archive(id, model, page);
        return  "redirect:/topic";
    }

    @GetMapping("/unarchive/{id}/currentPage/{page}")
    public String unarchiveTopic(@PathVariable UUID id,
                                 Model model,
                                 @PathVariable Integer page) {
        topicService.unarchive(id, model, page);
        return  "redirect:/topic";
    }

}
