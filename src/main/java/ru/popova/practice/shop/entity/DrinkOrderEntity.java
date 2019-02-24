package ru.popova.practice.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
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
    @OneToMany(mappedBy = "drinkOrder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ToppingForDrinkInOrderEntity> toppings = new ArrayList<>();

    public void addTopping(ToppingForDrinkInOrderEntity topping) {
        topping.setDrinkOrder(this);
        toppings.add(topping);
    }

}
