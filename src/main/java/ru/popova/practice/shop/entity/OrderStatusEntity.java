package ru.popova.practice.shop.entity;


import lombok.Getter;
import lombok.Setter;
import ru.popova.practice.shop.entity.entity_enum.OrderStatusId;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "order_status")
public class OrderStatusEntity{
    @Id
    @Enumerated(EnumType.ORDINAL)
    private OrderStatusId id;
    private String name;

}
