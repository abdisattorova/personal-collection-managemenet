package com.itransition.itransitioncoursework.entity;
//Sevinch Abdisattorova 06/08/2022 12:34 PM

import com.itransition.itransitioncoursework.entity.enums.RoleEnum;
import com.itransition.itransitioncoursework.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "roles")
@PackagePrivate
public class Role extends AbsEntity {

    @Cascade(CascadeType.ALL)
    @Enumerated(EnumType.STRING)
    RoleEnum roleEnum;

}

