package com.itransition.itransitioncoursework.dto;
//Sevinch Abdisattorova 06/18/2022 9:41 PM


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.PackagePrivate;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@PackagePrivate
public class CollectionDto {


    @NotNull(message = "Name can't be null!")
    String name;

    @NotNull(message = "Description can't be null!")
    String description;

    MultipartFile image;

    @NotNull
    UUID topicId;

    List<CustomFieldDto> customFields;
}
