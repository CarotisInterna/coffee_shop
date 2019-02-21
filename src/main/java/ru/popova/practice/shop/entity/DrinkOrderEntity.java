package ru.popova.practice.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Класс сущности напитка в заказе
 */
@Getter
@Setter
@Entity
@Table(name = "drink_order")
public class DrinkOrderEntity extends AbstractCoffeeShopEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Заказанный напиток
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private DrinkEntity drink;

    /**
     * Заказ
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderEntity order;

    /**
     * Количество напитка в этом заказе
     */
    private Integer quantity;

    /**
     * Список топпингов для напитка в этом заказе
     */
    @OneToMany(mappedBy = "drinkOrder")
    private List<ToppingForDrinkInOrderEntity> toppings;

}
