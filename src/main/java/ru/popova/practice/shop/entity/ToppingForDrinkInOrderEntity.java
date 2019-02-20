package ru.popova.practice.shop.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * класс сущности топпинга для напитка в заказе
 */
@Getter
@Setter
@Entity
@Table(name = "topping_for_drink_in_order")
public class ToppingForDrinkInOrderEntity extends AbstractCoffeeShopEntity<ToppingForDrinkInOrderId> {

    /**
     * ID напитка в заказе + id топпинга
     */
    @EmbeddedId
    private ToppingForDrinkInOrderId id;

    /**
     * Напиток в заказе
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("drinkOrderId")
    private DrinkOrderEntity drinkOrder;

    /**
     * Топпинг
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("toppingId")
    private ToppingEntity topping;

    /**
     * Количество топпинга для напитка в заказе
     */
    private Integer quantity;

}
