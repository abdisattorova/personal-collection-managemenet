package com.itransition.itransitioncoursework.dto;
//Sevinch Abdisattorova 06/27/2022 12:37 AM

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemDto {

    private UUID collectionId;

    private String name;

    private List<UUID> tagIds;


}
