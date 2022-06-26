package com.itransition.itransitioncoursework.service;
//Sevinch Abdisattorova 06/20/2022 1:50 PM

import com.itransition.itransitioncoursework.entity.Topic;
import com.itransition.itransitioncoursework.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;

    @Value("${TOPIC_SIZE}")
    private Integer size;


    public List<Topic> getAllTopics() {
        return topicRepository.findAllTopics(false);
    }


    public List<Topic> getAll() {
        return topicRepository.findAll();
    }


    public Page<Topic> getTopicsByPage(Integer page) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return topicRepository.findAll(pageable);
    }


    public String addNewTopic(Topic topic, RedirectAttributes model, Integer currentPage) {

        boolean isAdding = topic.getId() == null;

        boolean existsByName = topicRepository.existsByName(topic.getName());
        if (!existsByName) {
            topicRepository.save(topic);
            model.addFlashAttribute("success", true);
            if (isAdding) {
                model.addFlashAttribute("message", "New topic successfully added!");
            } else {
                model.addFlashAttribute("message", "Topic successfully edited!");
            }
            return "redirect:/topic?page=" + countAllPages();
        } else {
            model.addFlashAttribute("success", false);
            if (isAdding) {
                model.addFlashAttribute("message", "Topic already exists");
            } else {
                model.addFlashAttribute("message", "Topic not edited!");
            }
            return "redirect:/topic?page=" + currentPage;
        }
    }


    public String deleteTopic(UUID id, RedirectAttributes model, Integer currentPage) {
        try {
            topicRepository.deleteById(id);
            model.addFlashAttribute("success", true);
            model.addFlashAttribute("message", "Topic successfully deleted!");
            if (currentPage > countAllPages()) {
                currentPage--;
            }
        } catch (Exception e) {
            model.addFlashAttribute("success", false);
            model.addFlashAttribute("message", "You can't delete this!");
        }
        return "redirect:/topic?page=" + currentPage;

    }


    public String archive(UUID id, RedirectAttributes model, Integer currentPage) {
        try {
            Optional<Topic> byId = topicRepository.findById(id);
            if (byId.isPresent()) {
                Topic topic = byId.get();
                topic.setDeleted(true);
                topicRepository.save(topic);
            }
            model.addFlashAttribute("success", true);
            model.addFlashAttribute("message", "Topic successfully archived!");
            return "redirect:/topic?page=" + countAllPages();
        } catch (Exception e) {
            model.addFlashAttribute("success", false);
            model.addFlashAttribute("message", "Error!");
            return "redirect:/topic?page=" + currentPage;
        }

    }


    public String unarchive(UUID id, RedirectAttributes model, Integer currentPage) {

        try {
            Optional<Topic> byId = topicRepository.findById(id);
            if (byId.isPresent()) {
                Topic topic = byId.get();
                topic.setDeleted(false);
                topicRepository.save(topic);
            }
            model.addFlashAttribute("success", true);
            model.addFlashAttribute("message", "Topic successfully unarchived!");
            return "redirect:/topic?page=" + countAllPages();
        } catch (Exception e) {
            model.addFlashAttribute("success", false);
            model.addFlashAttribute("message", "Error!");
            return "redirect:/topic?page=" + currentPage;
        }

    }


    public int countAllPages() {
        Integer count = topicRepository.countAll();
        int pages = count / size;
        if (count % size > 0) {
            pages += 1;
        }
        return pages;
    }


}
