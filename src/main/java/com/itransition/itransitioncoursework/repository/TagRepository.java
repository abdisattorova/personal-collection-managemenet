package com.itransition.itransitioncoursework.repository;

import com.itransition.itransitioncoursework.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {

    boolean existsByName(String name);


    @Query(nativeQuery = true, value = " select t.* " +
            "from tags t " +
            "         join items_tags it on t.id = it.tag_id " +
            "where it.item_id = :itemId")
    List<Tag> getTagsOfItem(UUID itemId);

}
