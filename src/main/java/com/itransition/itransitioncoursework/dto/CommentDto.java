package com.itransition.itransitioncoursework.dto;
//Sevinch Abdisattorova 07/02/2022 9:45 AM


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDto {

    UUID id;

    private String content;

    private UUID itemId;
}
