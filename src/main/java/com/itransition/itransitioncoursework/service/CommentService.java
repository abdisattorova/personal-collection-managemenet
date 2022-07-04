package com.itransition.itransitioncoursework.service;
//Sevinch Abdisattorova 07/02/2022 9:42 AM


import com.itransition.itransitioncoursework.dto.CommentDto;
import com.itransition.itransitioncoursework.entity.Comment;
import com.itransition.itransitioncoursework.entity.Item;
import com.itransition.itransitioncoursework.projection.CommentProjection;
import com.itransition.itransitioncoursework.repository.CommentRepository;
import com.itransition.itransitioncoursework.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;


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
        // TODO: 07/02/2022  if item not found
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
        // TODO: 07/02/2022  if item not found
        return "redirect:/item/details/" + commentDto.getItemId() + "?error";
    }
}
