package com.itransition.itransitioncoursework.service;
//Sevinch Abdisattorova 06/15/2022 11:03 AM


import com.itransition.itransitioncoursework.dto.RegistrationDto;
import com.itransition.itransitioncoursework.entity.User;
import com.itransition.itransitioncoursework.mapper.UserMapper;
import com.itransition.itransitioncoursework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final OAuth2AuthorizedClientService authorizedClientService;


    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Email or Password is incorrect. Try again");
        }
        return user;
    }


    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    public void save(RegistrationDto registrationDto) {
        registrationDto.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        User user = userMapper.userRegistrationDtoToUser(registrationDto);
        userRepository.save(user);
    }


    public String oauthSuccess(OAuth2AuthenticationToken authentication) {

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

            HttpEntity<?> entity = new HttpEntity<>("", headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    userInfoEndpointUri,
                    HttpMethod.GET,
                    entity,
                    Map.class);

            Map userAttributes = response.getBody(); // user information

            assert userAttributes != null;
            String name = (String) userAttributes.get("name");
            String email = (String) userAttributes.get("email");
            String[] s = name.split(" ");
            User userByEmail = userRepository.findByEmail(email);
            if (userByEmail == null) {
                User user = new
                        User(s[0], s[1], email);
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
