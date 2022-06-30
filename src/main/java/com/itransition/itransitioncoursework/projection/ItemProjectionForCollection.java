package com.itransition.itransitioncoursework.projection;
//Sevinch Abdisattorova 06/28/2022 10:18 PM


import com.itransition.itransitioncoursework.entity.Tag;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.UUID;

public interface ItemProjectionForCollection {

    UUID getId();

    String getName();

    String getCreatedDate();


    @Value("#{@fieldValueRepository.getImageUrlOfItemIfExists(target.id)}")
    String getImageUrl();

    @Value("#{@tagRepository.getTagsOfItem(target.id)}")
    List<Tag> getTags();
}
