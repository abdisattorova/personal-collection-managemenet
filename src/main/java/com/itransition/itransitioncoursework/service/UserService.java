package com.itransition.itransitioncoursework.service;
//Sevinch Abdisattorova 06/25/2022 3:09 PM


import com.itransition.itransitioncoursework.entity.Role;
import com.itransition.itransitioncoursework.entity.User;
import com.itransition.itransitioncoursework.entity.enums.RoleEnum;
import com.itransition.itransitioncoursework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Value("${USERS_SIZE}")
    private Integer size;


    public Page<User> getUsersByPage(Integer page) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<User> users = userRepository.findAll(pageable);
        return users;
    }


    public String deleteUser(UUID id, RedirectAttributes model, Integer page) {
        try {
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
}
