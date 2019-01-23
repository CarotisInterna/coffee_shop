package ru.popova.practice.shop.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "topping_for_drink_in_order")
public class ToppingForDrinkInOrderEntity extends AbstractCoffeeShopEntity<ToppingForDrinkInOrderId> {

    @EmbeddedId
    private ToppingForDrinkInOrderId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("drinkOrderId")
    private DrinkOrderEntity drinkOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("toppingId")
    private ToppingEntity topping;

}
