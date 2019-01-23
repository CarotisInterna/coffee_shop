package ru.popova.practice.shop.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(of = {"drinkOrderId", "toppingId"})
@AllArgsConstructor
@NoArgsConstructor
public class ToppingForDrinkInOrderId implements Serializable {
    private Integer drinkOrderId;
    private Integer toppingId;
}
