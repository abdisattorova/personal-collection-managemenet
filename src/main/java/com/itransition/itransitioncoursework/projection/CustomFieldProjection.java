package com.itransition.itransitioncoursework.projection;

import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

public interface CustomFieldProjection {

    UUID getId();

    String getName();

    String getFieldDataType();


    @Value("#{@fieldValueRepository.findFieldByFieldId(target.id)}")
    String getValue();
}
