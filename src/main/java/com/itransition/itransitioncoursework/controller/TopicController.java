package com.itransition.itransitioncoursework.controller;
//Sevinch Abdisattorova 06/19/2022 8:31 PM

import com.itransition.itransitioncoursework.entity.Topic;
import com.itransition.itransitioncoursework.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return "topic";
    }


    @PostMapping
    public Object addTopic(Topic topic,
                           RedirectAttributes model,
                           @RequestParam Integer currentPage) {
        if (topic.getName().equals("")) {
            model.addFlashAttribute("success", false);
            model.addFlashAttribute("message", "Topic can't be empty");
            return "redirect:/topic";
        }
        return topicService.addNewTopic(topic, model, currentPage);
    }


    @PostMapping("/delete")
    public String deleteTopic(@RequestParam UUID id,
                              RedirectAttributes model,
                              @RequestParam Integer page) {
        return topicService.deleteTopic(id, model, page);
    }

    @PostMapping("/archive")
    public String archiveTopic(@RequestParam UUID id,
                               RedirectAttributes model,
                               @RequestParam Integer page) {
        return topicService.archive(id, model, page);
    }

    @PostMapping("/restore")
    public String unarchiveTopic(@RequestParam UUID id,
                                 RedirectAttributes model,
                                 @RequestParam Integer page) {
        return topicService.unarchive(id, model, page);
    }

}
