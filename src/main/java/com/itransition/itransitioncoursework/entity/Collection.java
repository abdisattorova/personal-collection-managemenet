package com.itransition.itransitioncoursework.entity;
//Sevinch Abdisattorova 06/08/2022 12:34 PM

import com.itransition.itransitioncoursework.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "collections")
@PackagePrivate
@Indexed
public class Collection extends AbsEntity {

    @Field
    String name;

    @Field
    String description;

    String imageUrl;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    Topic topic;

    @OneToMany(mappedBy = "collection", cascade = CascadeType.ALL)
    List<Item> items;

    public Collection(String name, String description, String imageUrl, Topic topic) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.topic = topic;
    }

}

