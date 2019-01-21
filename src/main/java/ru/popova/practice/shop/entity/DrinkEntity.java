package ru.popova.practice.shop.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "drink")
public class DrinkEntity {

    @Id
    private Integer id;

    private String name;
    private Integer volume;
    private String description;

    @OneToMany(mappedBy = "drink")
    private List<DrinkCategoryEntity> drinkCategory;

    @OneToMany(mappedBy = "drink")
    private List<DrinkImageEntity> drinkImage;
}
