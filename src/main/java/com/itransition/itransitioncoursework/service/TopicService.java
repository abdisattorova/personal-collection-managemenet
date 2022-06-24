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
import org.springframework.ui.Model;

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


    public void addNewTopic(Topic topic, Model model, Integer currentPage) {

        boolean isAdding = topic.getId() == null;

        boolean existsByName = topicRepository.existsByName(topic.getName());
        if (!existsByName) {
            topicRepository.save(topic);
            getTopicsOfLastPage(model);
            model.addAttribute("success", true);
            if (isAdding) {
                model.addAttribute("message", "New topic successfully added!");
            } else {
                model.addAttribute("message", "Topic successfully edited!");
            }
        } else {
            Page<Topic> topicsByPage = getTopicsByPage(currentPage);
            model.addAttribute("topics", topicsByPage);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("pages", topicsByPage.getTotalPages());
            model.addAttribute("success", false);
            if (isAdding) {
                model.addAttribute("message", "Topic already exists");
            } else {
                model.addAttribute("message", "Topic not edited!");
            }
        }
    }


    public void deleteTopic(UUID id, Model model, Integer currentPage) {
        try {
            topicRepository.deleteById(id);
            getTopicsOfLastPage(model);
            model.addAttribute("success", true);
            model.addAttribute("message", "Topic successfully deleted!");
        } catch (Exception e) {
            Page<Topic> topicsByPage = getTopicsByPage(currentPage);
            model.addAttribute("topics", topicsByPage);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("pages", topicsByPage.getTotalPages());
            model.addAttribute("success", false);
            model.addAttribute("message", "You can't delete this!");
        }

    }

    public void archive(UUID id, Model model, Integer currentPage) {
        try {
            Optional<Topic> byId = topicRepository.findById(id);
            if (byId.isPresent()) {
                Topic topic = byId.get();
                topic.setDeleted(true);
                topicRepository.save(topic);
            }
            getTopicsOfLastPage(model);
            model.addAttribute("success", true);
            model.addAttribute("message", "Topic successfully archived!");
        } catch (Exception e) {
            Page<Topic> topicsByPage = getTopicsByPage(currentPage);
            model.addAttribute("topics", topicsByPage);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("pages", topicsByPage.getTotalPages());
            model.addAttribute("success", false);
            model.addAttribute("message", "Error!");
        }

    }

    public void unarchive(UUID id, Model model, Integer currentPage) {

        try {
            Optional<Topic> byId = topicRepository.findById(id);
            if (byId.isPresent()) {
                Topic topic = byId.get();
                topic.setDeleted(false);
                topicRepository.save(topic);
            }
            getTopicsOfLastPage(model);
            model.addAttribute("success", true);
            model.addAttribute("message", "Topic successfully unarchived!");
        } catch (Exception e) {
            Page<Topic> topicsByPage = getTopicsByPage(currentPage);
            model.addAttribute("topics", topicsByPage);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("pages", topicsByPage.getTotalPages());
            model.addAttribute("success", false);
            model.addAttribute("message", "Error!");
        }

    }


    private void getTopicsOfLastPage(Model model) {
        int count = countAllPages();
        Page<Topic> topicsByPage = getTopicsByPage(count);
        model.addAttribute("currentPage", count);
        model.addAttribute("pages", topicsByPage.getTotalPages());
        model.addAttribute("topics", topicsByPage);


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
