package com.itransition.itransitioncoursework.repository;
//Sevinch Abdisattorova 06/19/2022 9:21 AM


import com.itransition.itransitioncoursework.entity.CustomField;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomFieldRepository extends JpaRepository<CustomField, UUID> {
}
