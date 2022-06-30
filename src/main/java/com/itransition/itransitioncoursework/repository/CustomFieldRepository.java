package com.itransition.itransitioncoursework.repository;
//Sevinch Abdisattorova 06/19/2022 9:21 AM


import com.itransition.itransitioncoursework.entity.CustomField;
import com.itransition.itransitioncoursework.projection.CustomFieldProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CustomFieldRepository extends JpaRepository<CustomField, UUID> {

    @Query(nativeQuery = true,value = "select cast (id as varchar) as id, name, field_data_type as fieldDataType" +
            " from custom_fields " +
            " where collection_id = :collection_id ")
    List<CustomFieldProjection> findAllByCollectionId(UUID collection_id);

}
