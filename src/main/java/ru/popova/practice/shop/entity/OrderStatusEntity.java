package ru.popova.practice.shop.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "order_status")
public class OrderStatusEntity{
    @Id
    @Enumerated(EnumType.ORDINAL)
    private Integer id;
    private String name;

}
