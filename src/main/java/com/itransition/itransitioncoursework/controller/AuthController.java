package com.itransition.itransitioncoursework.controller;

import com.itransition.itransitioncoursework.dto.RegistrationDto;
import com.itransition.itransitioncoursework.entity.User;
import com.itransition.itransitioncoursework.repository.UserRepository;
import com.itransition.itransitioncoursework.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final OAuth2AuthorizedClientService authorizedClientService;
    private final UserService userService;
    private final UserRepository userRepository;


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

        if (userService.existsByEmail(registrationDto.getEmail())) {
            result.addError(new
                    FieldError("user",
                    "email",
                    "Email is already taken!"));
            return "registration";
        }
        userService.save(registrationDto);
        return "redirect:/login?registered";
    }

    @GetMapping("/success-oauth2")
    public String getLoginInfo(Model model,
                               OAuth2AuthenticationToken authentication) {

        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());

        String userInfoEndpointUri = client
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUri();

        if (!StringUtils.isEmpty(userInfoEndpointUri)) {

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();

            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + client.getAccessToken()
                    .getTokenValue());

            HttpEntity entity = new HttpEntity("", headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    userInfoEndpointUri,
                    HttpMethod.GET,
                    entity,
                    Map.class);

            Map userAttributes = response.getBody(); // user information

            String name = (String) userAttributes.get("name");
            String email = (String) userAttributes.get("email");
            String[] s = name.split(" ");
            User userByEmail = userRepository.findByEmail(email);
            if (userByEmail == null) {
                User user = User.builder().email(email).firstName(s[0]).lastName(s[1]).build();
                userByEmail = userRepository.save(user);
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userByEmail,
                    null,
                    userByEmail.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        return "redirect:/";
    }
}
