package ru.popova.practice.shop.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * класс сущности топпинга
 */
@Getter
@Setter
@Entity
@Table(name = "topping")
public class ToppingEntity extends AbstractCoffeeShopEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    /**
     *наименование топпинга
     */
    private String name;
    /**
     *цена за одну порцию топпинга
     */
    private BigDecimal price;

}
