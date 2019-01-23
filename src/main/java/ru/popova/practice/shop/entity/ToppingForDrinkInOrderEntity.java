package ru.popova.practice.shop.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "topping_for_drink_in_order")
public class ToppingForDrinkInOrderEntity {

    @OneToMany(mappedBy = "toppingForDrinkInOrder")
    private List<ToppingEntity> topping;

    @OneToMany(mappedBy = "toppingForDrinkInOrder")
    private List<DrinkOrderEntity> drinkOrder;

}
