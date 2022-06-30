package com.itransition.itransitioncoursework.dto;
//Sevinch Abdisattorova 06/27/2022 12:22 PM

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomFieldValueDto {

    private UUID customFieldId;
    private String value;
}
