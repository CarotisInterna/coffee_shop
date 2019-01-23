package ru.popova.practice.shop.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "topping")
public class ToppingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String name;
    private Double price;

    @ManyToOne
    private ToppingForDrinkInOrderEntity toppingForDrinkInOrder;

}
