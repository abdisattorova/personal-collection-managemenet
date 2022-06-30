package com.itransition.itransitioncoursework.projection;
//Sevinch Abdisattorova 06/28/2022 10:18 PM


import java.util.UUID;

public interface ItemProjection {

    UUID getId();

    UUID getCollectionId();

    String getName();

    String getCreatedDate();



}
