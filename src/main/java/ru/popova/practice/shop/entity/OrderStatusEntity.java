package ru.popova.practice.shop.entity;


import lombok.Getter;
import lombok.Setter;
import ru.popova.practice.shop.entity.code.OrderStatusCode;

import javax.persistence.*;

/**
 * класс сущности статуса заказа
 */
@Getter
@Setter
@Entity
@Table(name = "order_status")
public class OrderStatusEntity extends AbstractCoffeeShopEntity<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * код статуса заказа
     */
    @Enumerated(EnumType.STRING)
    private OrderStatusCode code;
    /**
     * наименование статуса заказа
     */
    @Column(name = "status_name")
    private String name;

}
