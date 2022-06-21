package com.itransition.itransitioncoursework.dto;
//Sevinch Abdisattorova 06/18/2022 10:53 PM

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.PackagePrivate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@PackagePrivate
public class CustomFieldDto {

    String fieldName;

    String fieldDataType;

}
