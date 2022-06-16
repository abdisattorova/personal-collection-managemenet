package com.itransition.itransitioncoursework.mapper;

import com.itransition.itransitioncoursework.dto.RegistrationDto;
import com.itransition.itransitioncoursework.entity.Role;
import com.itransition.itransitioncoursework.entity.User;
import com.itransition.itransitioncoursework.entity.enums.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

     private final ModelMapper mapper;

    public User userRegistrationDtoToUser(RegistrationDto dto) {
        User user = mapper.map(dto, User.class);
        user.setRole(new Role(RoleEnum.USER));
        return user;
    }
}
