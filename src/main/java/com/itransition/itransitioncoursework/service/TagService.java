package com.itransition.itransitioncoursework.service;
//Sevinch Abdisattorova 06/21/2022 5:02 PM

import com.itransition.itransitioncoursework.entity.Tag;
import com.itransition.itransitioncoursework.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;


    public void addNewTag(String tag) {
        boolean existsByName = tagRepository.existsByName(tag);
        if (!existsByName) {
            tagRepository.save(new Tag(tag));
        }
    }
}
