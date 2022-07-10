package com.itransition.itransitioncoursework.repository;


import com.itransition.itransitioncoursework.entity.FieldValue;
import com.itransition.itransitioncoursework.projection.CustomFieldValueProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FieldValueRepository extends JpaRepository<FieldValue, UUID> {


    @Query(nativeQuery = true, value = "select value " +
            "FROM field_values " +
            "         join custom_fields cf on cf.id = field_values.custom_field_id " +
            "where item_id = :itemId " +
            "  and cf.field_data_type = 'file'")
    String getImageUrlOfItemIfExists(UUID itemId);


    @Query(nativeQuery = true, value = "select cf.name as fieldName, fv.value as fieldValue " +
            "from field_values fv " +
            "         join custom_fields cf on cf.id = fv.custom_field_id " +
            "where item_id = :itemId " +
            " and cf.field_data_type != 'file'")
    List<CustomFieldValueProjection> getFieldValuesOfItem(UUID itemId);


    @Query(nativeQuery = true, value ="select value\n" +
            "from field_values\n" +
            "where custom_field_id = :fieldId")
    String findFieldByFieldId(UUID fieldId);
}
