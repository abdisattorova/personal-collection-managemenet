package com.itransition.itransitioncoursework.repository;

import com.itransition.itransitioncoursework.entity.Dislike;
import com.itransition.itransitioncoursework.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DislikeRepository extends JpaRepository<Dislike, UUID> {
}
