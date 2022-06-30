package com.itransition.itransitioncoursework.entity;
//Sevinch Abdisattorova 06/08/2022 12:34 PM

import com.itransition.itransitioncoursework.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "custom_fields")
@PackagePrivate
public class CustomField extends AbsEntity {

    String name;

    @Enumerated(EnumType.STRING)
    FieldDataType fieldDataType;

    @ManyToOne
    @JoinColumn(name = "collection_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    Collection collection;

}

