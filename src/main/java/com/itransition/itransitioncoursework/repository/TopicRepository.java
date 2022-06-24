package com.itransition.itransitioncoursework.repository;

import com.itransition.itransitioncoursework.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TopicRepository extends JpaRepository<Topic,UUID> {

    @Query(nativeQuery = true,value =
            "select * from topics where is_deleted=false")
    List<Topic> findAllTopics(boolean deleted);

    boolean existsByName(String name);

    Page<Topic> findAll(Pageable pageable);

    @Query(nativeQuery = true,value = "select  count (*) from topics ")
    Integer countAll();

}
