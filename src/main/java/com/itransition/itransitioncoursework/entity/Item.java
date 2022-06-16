package com.itransition.itransitioncoursework.entity;
//Sevinch Abdisattorova 06/08/2022 12:34 PM

import com.itransition.itransitioncoursework.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;

import javax.persistence.*;
import java.security.Permission;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "items")
@PackagePrivate
public class Item extends AbsEntity {

    String name;

    @ManyToOne
    @JoinColumn(name = "collection_id")
    Collection collection;

    @ManyToMany
    @JoinTable(
            name = "items_tags",
            joinColumns = {@JoinColumn(name = "item_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    List<Tag> tags;

}

