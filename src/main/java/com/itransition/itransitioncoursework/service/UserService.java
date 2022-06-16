package com.itransition.itransitioncoursework.service;
//Sevinch Abdisattorova 06/15/2022 11:03 AM


import com.itransition.itransitioncoursework.dto.RegistrationDto;
import com.itransition.itransitioncoursework.entity.User;
import com.itransition.itransitioncoursework.mapper.UserMapper;
import com.itransition.itransitioncoursework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Email or Password is incorrect. Try again");
        }
        return user;
    }

    public void save(RegistrationDto registrationDto) {
        registrationDto.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        User user = userMapper.userRegistrationDtoToUser(registrationDto);
        userRepository.save(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
