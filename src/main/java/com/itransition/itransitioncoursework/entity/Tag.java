package com.itransition.itransitioncoursework.entity;
//Sevinch Abdisattorova 06/08/2022 12:34 PM

import com.itransition.itransitioncoursework.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "tags")
@PackagePrivate
public class Tag extends AbsEntity {

    String name;
    @ManyToMany(mappedBy = "tags")
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<Item> itemTags;

    public Tag(String name) {
        this.name = name;
    }

    @PreRemove
    private void remove() {
        for (Item itemTag : itemTags) {
            itemTag.getTags().remove(this);
        }
    }

}

