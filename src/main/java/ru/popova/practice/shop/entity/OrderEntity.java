package ru.popova.practice.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    /**
     *
     */
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private OrderStatusEntity orderStatus;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private AppUserEntity appUser;

    private Double total;
    private String address;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @OneToMany(mappedBy = "order")
    private List<DrinkOrderEntity> drinkOrder;


}
