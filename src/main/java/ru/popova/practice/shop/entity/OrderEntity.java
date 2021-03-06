package ru.popova.practice.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * класс сущности заказа
 */
@Entity
@Getter
@Setter
@Table(name = "orders")
public class OrderEntity extends AbstractCoffeeShopEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     *статус заказа
     */
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private OrderStatusEntity orderStatus;

    /**
     *id покупателя (пользователя)
     */
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private AppUserEntity appUser;

    /**
     *сумма заказа
     */
    private BigDecimal total;
    /**
     *адрес доставки заказа
     */
    private String address;

    /**
     *дата заказа
     */
    private LocalDateTime date;

    /**
     *список напитков в заказе
     */
    @OneToMany(mappedBy = "order",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DrinkOrderEntity> drinks = new ArrayList<>();

    public void addDrink(DrinkOrderEntity drinkOrder) {
        drinkOrder.setOrder(this);
        drinks.add(drinkOrder);
    }

}
