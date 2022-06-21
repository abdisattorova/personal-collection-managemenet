package com.itransition.itransitioncoursework.service;
//Sevinch Abdisattorova 06/20/2022 1:50 PM

import com.itransition.itransitioncoursework.entity.Topic;
import com.itransition.itransitioncoursework.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;


    public ResponseEntity<?> getAllTopics() {
        List<Topic> topics = topicRepository.findAll();
        return ResponseEntity.ok(topics);
    }
}
