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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "dislikes")
@PackagePrivate
public class Dislike extends AbsEntity {

   @ManyToOne
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JoinColumn(name = "user_id")
   User user;

   @ManyToOne
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JoinColumn(name = "item_id")
   Item item;

}

