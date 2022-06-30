package com.itransition.itransitioncoursework.dto;
//Sevinch Abdisattorova 06/18/2022 9:41 PM


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CollectionDto {

    @NotNull(message = "Name can't be null!")
    @Size(min = 2, message = "AXscdvfgf")
    private String name;

    @NotNull(message = "Description can't be null!")
    private String description;

    @NotNull
    private UUID topicId;

    private MultipartFile image;

    private List<CustomFieldDto> customFields;
}
