package ru.popova.practice.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * класс сущности изображения напитка
 */
@Entity
@Getter
@Setter
@Table(name = "drink_img")
public class DrinkImageEntity extends AbstractCoffeeShopEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     *путь до изображения в файловой системе
     */
    private String image;

    @ManyToOne
    private DrinkEntity drink;
}
