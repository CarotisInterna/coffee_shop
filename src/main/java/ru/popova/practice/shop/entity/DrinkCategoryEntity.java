package ru.popova.practice.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Table(name = "drink_category")
public class DrinkCategoryEntity {

    @EmbeddedId
    private DrinkCategoryId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("drinkId")
    private DrinkEntity drinkEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("categoryId")
    private CategoryEntity categoryEntity;

}
