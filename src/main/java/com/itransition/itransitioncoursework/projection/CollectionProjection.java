package com.itransition.itransitioncoursework.projection;


import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

public interface CollectionProjection {

    UUID getId();

    String getName();

    String getImageUrl();


    Integer getItemsCount();

    @Value("#{@collectionRepository.getCreatedDateOfCollection(target.id)}")
    String getCreatedDate();
}
