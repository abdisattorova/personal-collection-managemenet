package com.itransition.itransitioncoursework.repository;

import com.itransition.itransitioncoursework.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TagRepository  extends JpaRepository<Tag, UUID> {

    boolean existsByName(String name);


}
