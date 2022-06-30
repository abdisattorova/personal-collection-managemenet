package com.itransition.itransitioncoursework.projection;


import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.UUID;

public interface CollectionDetailsProjection {

    UUID getId();

    String getName();

    String getDescription();

    String getImageUrl();

    UUID getTopicId();

    String getTopicName();

    Integer getItemsCount();

    String getAuthorName();

    UUID getAuthorId();


    @Value("#{@collectionRepository.getCreatedDateOfCollection(target.id)}")
    String getCreatedDate();

    @Value("#{@itemRepository.getItemsOfCollection(target.id)}")
    List<ItemProjectionForCollection> getItems();

}
