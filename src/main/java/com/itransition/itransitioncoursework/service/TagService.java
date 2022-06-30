package com.itransition.itransitioncoursework.service;
//Sevinch Abdisattorova 06/21/2022 5:02 PM

import com.itransition.itransitioncoursework.entity.Tag;
import com.itransition.itransitioncoursework.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;


    public boolean addNewTag(Tag tag, Model model) {
        boolean existsByName = tagRepository.existsByName(tag.getName());
        if (!existsByName) {
            tagRepository.save(tag);
            List<Tag> tagList = getAll();
            model.addAttribute("tags", tagList);
            return true;
        }
        return false;
    }


    public List<Tag> getAll() {
        return tagRepository.findAll();
    }



    public Tag getTagById(UUID id){
        return tagRepository.findById(id).get();
    }


    public String deleteTag(UUID id, Model model) {
        try {
            tagRepository.deleteById(id);
            List<Tag> tags = getAll();
            model.addAttribute("tags", tags);
            return "redirect:/tag?deleted";
        } catch (Exception e) {
            return "redirect:/tag?error";
        }
    }
}
