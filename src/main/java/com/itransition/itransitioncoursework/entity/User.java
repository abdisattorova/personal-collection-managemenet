package com.itransition.itransitioncoursework.entity;
//Sevinch Abdisattorova 06/08/2022 12:29 PM

import com.itransition.itransitioncoursework.entity.enums.RoleEnum;
import com.itransition.itransitioncoursework.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@PackagePrivate
@Entity(name = "users")
@Builder
public class User extends AbsEntity implements UserDetails {

    String firstName;

    String lastName;

    String email;

    String password = "password";

    @Column(columnDefinition = "boolean default false")
    Boolean isBlocked = false;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    Role role = new Role(RoleEnum.USER);

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(role.getRoleEnum().toString()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isBlocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
