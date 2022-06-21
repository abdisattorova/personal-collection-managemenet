package com.itransition.itransitioncoursework.repository;

import com.itransition.itransitioncoursework.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CollectionRepository extends JpaRepository<Collection, UUID> {
}
