package com.itransition.itransitioncoursework.projection;
//Sevinch Abdisattorova 06/28/2022 10:18 PM


import com.itransition.itransitioncoursework.entity.Tag;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.UUID;

public interface ItemProjectionForDetail {


    UUID getId();


    String getName();


    String getCreatedDate();


    String getCollectionName();


    UUID getCollectionId();


    String getAuthorName();


    UUID getAuthorId();


    @Value("#{@itemRepository.getLikesCount(target.id)}")
    Integer getLikesCount();


    @Value("#{@itemRepository.getDislikesCount(target.id)}")
    Integer getDislikesCount();


    @Value("#{@fieldValueRepository.getImageUrlOfItemIfExists(target.id)}")
    String getImageUrl();


    @Value("#{@tagRepository.getTagsOfItem(target.id)}")
    List<Tag> getTags();



    @Value("#{@fieldValueRepository.getFieldValuesOfItem(target.id)}")
    List<CustomFieldValueProjection> getCustomFieldValues();


    @Value("#{@commentRepository.getCommentsOfItem(target.id)}")
    List<CommentProjection> getComments();
}
