package ru.popova.practice.shop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * класс сущности изображения напитка
 */
@Entity
@Getter
@Setter
@Table(name = "drink_img")
@NoArgsConstructor
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

    public DrinkImageEntity(String image, DrinkEntity drink) {
        this.image = image;
        this.drink = drink;
    }
}
