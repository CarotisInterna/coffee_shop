package ru.popova.practice.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "drink_order")
public class DrinkOrderEntity extends AbstractCoffeeShopEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private DrinkEntity drink;

    @ManyToOne(fetch = FetchType.LAZY)
    private OrderEntity order;

    private Integer quantity;

    @OneToMany(mappedBy = "drinkOrder")
    private List<ToppingForDrinkInOrderEntity> toppings;

}
