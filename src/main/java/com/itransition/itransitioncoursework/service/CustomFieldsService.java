package com.itransition.itransitioncoursework.service;
//Sevinch Abdisattorova 06/27/2022 12:13 AM


import com.itransition.itransitioncoursework.entity.CustomField;
import com.itransition.itransitioncoursework.projection.CustomFieldProjection;
import com.itransition.itransitioncoursework.repository.CustomFieldRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomFieldsService {

    private final CustomFieldRepository customFieldRepository;


    List<CustomFieldProjection> getCustomFieldsOfCollection(UUID collectionId) {
        return customFieldRepository.findAllByCollectionId(collectionId);
    }


    CustomField getCustomFieldById(UUID id){
        return customFieldRepository.findById(id).get();
    }

}
