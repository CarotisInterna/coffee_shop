package ru.popova.practice.shop.entity;


import lombok.Getter;
import lombok.Setter;
import ru.popova.practice.shop.entity.id.OrderStatusId;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "order_status")
public class OrderStatusEntity extends AbstractCoffeeShopEntity<OrderStatusId> {
    @Id
    @Enumerated(EnumType.ORDINAL)
    private OrderStatusId id;
    private String name;

}
