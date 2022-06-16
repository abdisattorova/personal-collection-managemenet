package com.itransition.itransitioncoursework.entity;
//Sevinch Abdisattorova 06/08/2022 12:34 PM

import com.itransition.itransitioncoursework.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "collections")
@PackagePrivate
public class Collection extends AbsEntity {

    String name;

    String description;

    String imageUrl;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    Topic topic;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}

