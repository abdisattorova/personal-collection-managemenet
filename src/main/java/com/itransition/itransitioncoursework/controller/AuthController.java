package com.itransition.itransitioncoursework.controller;

import com.itransition.itransitioncoursework.dto.RegistrationDto;
import com.itransition.itransitioncoursework.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;


    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("user", new RegistrationDto());
        return "registration";
    }


    @PostMapping("/registration")
    public String registerUser(
            @Valid
            @ModelAttribute("user") RegistrationDto registrationDto,
            BindingResult result) {
        if (result.hasErrors()) {
            return "registration";
        }

        if (authService.existsByEmail(registrationDto.getEmail())) {
            result.addError(new
                    FieldError("user",
                    "email",
                    "Email is already taken!"));
            return "registration";
        }

        authService.save(registrationDto);
        return "redirect:/login?registered";
    }


    @GetMapping("/success-oauth2")
    public String getLoginInfo(
            OAuth2AuthenticationToken authentication) {

        return authService.oauthSuccess(authentication);

    }
}
