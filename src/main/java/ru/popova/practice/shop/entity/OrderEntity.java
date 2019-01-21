package ru.popova.practice.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp timestamp;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private OrderStatusEntity orderStatus;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private AppUserEntity appUser;

}
