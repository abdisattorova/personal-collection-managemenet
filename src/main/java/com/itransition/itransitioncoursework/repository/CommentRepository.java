package com.itransition.itransitioncoursework.repository;

import com.itransition.itransitioncoursework.entity.Comment;
import com.itransition.itransitioncoursework.projection.CommentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {


    @Query(nativeQuery = true, value = "select cast(c.id as varchar) as id, " +
            "       c.content                                as content, " +
            "       cast(u.id as varchar)                  as authorId, " +
            "       concat(u.first_name, ' ', u.last_name) as authorName, " +
            "       trim(TO_CHAR(c.updated_at, 'dd')) || ' ' || " +
            "       trim(TO_CHAR(c.updated_at, 'Month')) || ' ' || " +
            "       trim(TO_CHAR(c.updated_at, 'yyyy')) as date, " +
            "       trim(TO_CHAR(c.updated_at, 'HH24:MI')) as time " +
            "from comments c " +
            "         join users u on c.created_by_id = u.id " +
            "where item_id = :itemId")
    List<CommentProjection> getCommentsOfItem(UUID itemId);


    void deleteAllByCreatedBy(UUID id);
}
