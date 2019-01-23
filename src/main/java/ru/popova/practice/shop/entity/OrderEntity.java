package ru.popova.practice.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class OrderEntity extends AbstractCoffeeShopEntity<Integer>{

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

    private BigDecimal total;
    private String address;

    private LocalDateTime date;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<DrinkOrderEntity> drinks;


}
