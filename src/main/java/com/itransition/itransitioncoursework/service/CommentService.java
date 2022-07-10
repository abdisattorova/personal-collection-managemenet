package com.itransition.itransitioncoursework.service;
//Sevinch Abdisattorova 07/02/2022 9:42 AM


import com.itransition.itransitioncoursework.dto.CommentDto;
import com.itransition.itransitioncoursework.entity.Comment;
import com.itransition.itransitioncoursework.entity.Item;
import com.itransition.itransitioncoursework.projection.CommentProjection;
import com.itransition.itransitioncoursework.repository.CommentRepository;
import com.itransition.itransitioncoursework.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;
    private final EntityManager entityManager;

    public List<CommentProjection> getCommentsOfItem(UUID itemId) {
        return commentRepository.getCommentsOfItem(itemId);
    }


    public String addComment(CommentDto commentDto) {

        Optional<Item> byId = itemRepository.findById(commentDto.getItemId());
        if (byId.isPresent()) {
            Item item = byId.get();
            Comment comment = new Comment(commentDto.getContent(), item);
            commentRepository.save(comment);
            return "redirect:/item/details/" + commentDto.getItemId();
        }

        return "redirect:/item/details/" + commentDto.getItemId() + "?error";
    }

    public String deleteComment(UUID id) {
        Optional<Comment> commentById = commentRepository.findById(id);
        Comment comment = commentById.get();
        Item item = comment.getItem();
        try {
            commentRepository.deleteById(id);
            return "redirect:/item/details/" + item.getId();
        } catch (Exception e) {
            return "redirect:/item/details/" + item.getId() + "?error";
        }

    }

    public String editComment(CommentDto commentDto) {
        Optional<Item> byId = itemRepository.findById(commentDto.getItemId());
        if (byId.isPresent()) {
            Item item = byId.get();
            UUID id = commentDto.getId();
            Comment comment = commentRepository.findById(id).get();
            comment.setContent(commentDto.getContent());
            commentRepository.save(comment);
            return "redirect:/item/details/" + commentDto.getItemId();
        }
        return "redirect:/item/details/" + commentDto.getItemId() + "?error";
    }

    public List<Comment> getCommentsBySearch(String text,
                                             FullTextEntityManager fullTextEntityManager) {
        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Comment.class)
                .get();

        org.apache.lucene.search.Query query = queryBuilder
                .keyword()
                .wildcard()
                .onField("content")
                .matching("*" + text + "*")
                .createQuery();

        org.hibernate.search.jpa.FullTextQuery jpaQuery
                = fullTextEntityManager.createFullTextQuery(query,
                Comment.class);
        return jpaQuery.getResultList();
    }

}
