package com.itransition.itransitioncoursework.service;
//Sevinch Abdisattorova 06/25/2022 3:09 PM


import com.itransition.itransitioncoursework.entity.Role;
import com.itransition.itransitioncoursework.entity.User;
import com.itransition.itransitioncoursework.entity.enums.RoleEnum;
import com.itransition.itransitioncoursework.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CollectionRepository collectionRepository;
    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final DislikeRepository dislikeRepository;

    @Value("${USERS_SIZE}")
    private Integer size;


    public Page<User> getUsersByPage(Integer page) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<User> users = userRepository.findAll(pageable);
        return users;
    }


    public String deleteUser(UUID id, RedirectAttributes model, Integer page) {
        try {
            deleteEverythingUserCreated(id);
            userRepository.deleteById(id);
            if (page > countAllPages()) {
                page--;
            }
            model.addFlashAttribute("success", true);
            model.addFlashAttribute("message", "User successfully deleted!");
        } catch (Exception e) {
            model.addFlashAttribute("success", false);
            model.addFlashAttribute("message", "User could not be deleted!");
        }
        return "redirect:/admin/users?page=" + page;
    }


    public String blockUser(UUID id, RedirectAttributes model, Integer page) {

        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            user.setIsBlocked(true);
            userRepository.save(user);
            model.addFlashAttribute("success", true);
            model.addFlashAttribute("message", "User successfully blocked!");
            return "redirect:/admin/users?page=" + countAllPages();
        } else {
            model.addFlashAttribute("success", false);
            model.addFlashAttribute("message", "User not found!");
            return "redirect:/admin/users?page=" + page;
        }
    }


    public String unblockUser(UUID id, RedirectAttributes model, Integer page) {


        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            user.setIsBlocked(false);
            userRepository.save(user);
            model.addFlashAttribute("success", true);
            model.addFlashAttribute("message", "User successfully unblocked!");
            return "redirect:/admin/users?page=" + countAllPages();
        } else {
            model.addFlashAttribute("success", false);
            model.addFlashAttribute("message", "User not found!");
            return "redirect:/admin/users?page=" + page;
        }

    }


    public String promoteUserToAdmin(UUID id, RedirectAttributes model, Integer page) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            user.setRole(new Role(RoleEnum.ADMIN));
            userRepository.save(user);
            model.addFlashAttribute("success", true);
            model.addFlashAttribute("message", "User successfully promoted to admin!");
            return "redirect:/admin/users?page=" + countAllPages();
        } else {
            model.addFlashAttribute("success", false);
            model.addFlashAttribute("message", "User not found!");
            return "redirect:/admin/users?page=" + page;
        }
    }

    public String removeUserFromAdmin(UUID id, RedirectAttributes model, Integer page) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            user.setRole(new Role(RoleEnum.USER));
            userRepository.save(user);
            model.addFlashAttribute("success", true);
            model.addFlashAttribute("message", "User successfully removed from admin!");
            return "redirect:/admin/users?page=" + countAllPages();
        } else {
            model.addFlashAttribute("success", false);
            model.addFlashAttribute("message", "User not found!");
            return "redirect:/admin/users?page=" + page;
        }
    }


    private int countAllPages() {
        Integer count = userRepository.countAll();
        int pages = count / size;
        if (count % size > 0) {
            pages += 1;
        }
        return pages;
    }


    public Integer getAdminsCount() {
        return userRepository.countUsers();
    }


    public Integer getUsersCount() {
        return userRepository.countAdmins();
    }


    public String editUser(User user, RedirectAttributes model) {
        UUID id = user.getId();
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            if (user.getPassword() != null && user.getPassword().length() > 0) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            userRepository.save(user);
            model.addFlashAttribute("success", true);
            model.addFlashAttribute("message", "Saved!");
            model.addFlashAttribute("currentUser", user);
            return "redirect:/";

        }
        model.addFlashAttribute("success", false);
        model.addFlashAttribute("message", "Error!");
        return "redirect:/";
    }


    public String deleteProfile(UUID id, RedirectAttributes attributes) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            try {
                deleteEverythingUserCreated(id);
                userRepository.deleteById(id);
                SecurityContextHolder.getContext().setAuthentication(null);
                attributes.addFlashAttribute("currentUser", null);
                attributes.addFlashAttribute("success", true);
                attributes.addFlashAttribute("message", "Success!");
                return "redirect:/";
            } catch (Exception e) {
                attributes.addFlashAttribute("success", false);
                attributes.addFlashAttribute("message", "Error!");
                return "redirect:/";
            }
        }
        attributes.addFlashAttribute("success", false);
        attributes.addFlashAttribute("message", "Error!");
        return "redirect:/";
    }


    void deleteEverythingUserCreated(UUID id) {
        try {
            itemRepository.deleteAllByCreatedBy(id);
            collectionRepository.deleteAllByCreatedBy(id);
            commentRepository.deleteAllByCreatedBy(id);
        } catch (Exception ignored) {
        }
    }
}
