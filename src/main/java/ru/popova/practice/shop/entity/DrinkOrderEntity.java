package ru.popova.practice.shop.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.loader.entity.CascadeEntityJoinWalker;

import javax.persistence.*;
import java.util.List;

/**
 * класс сущности напитка в заказе
 */
@Getter
@Setter
@Entity
@Table(name = "drink_order")
public class DrinkOrderEntity extends AbstractCoffeeShopEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    /**
     *id заказанного напитка
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private DrinkEntity drink;

    /**
     *id заказа
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderEntity order;

    /**
     *количество напитка в этом заказе
     */
    private Integer quantity;

    /**
     *список топпингов для напитка в этом заказе
     */
    @OneToMany(mappedBy = "drinkOrder")
    private List<ToppingForDrinkInOrderEntity> toppings;

}
