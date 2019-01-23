package ru.popova.practice.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "drink_order")
public class DrinkOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @EmbeddedId
    private DrinkCategoryId drinkCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("drinkId")
    private DrinkEntity drink;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    private OrderEntity order;

    private Integer quantity;

    @ManyToOne
    private ToppingForDrinkInOrderEntity toppingForDrinkInOrder;

}
