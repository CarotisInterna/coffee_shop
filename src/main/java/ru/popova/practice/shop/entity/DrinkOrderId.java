package ru.popova.practice.shop.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"drinkId", "orderId"})
public class DrinkOrderId implements Serializable {
    private Integer orderId;
    private Integer drinkId;
}
