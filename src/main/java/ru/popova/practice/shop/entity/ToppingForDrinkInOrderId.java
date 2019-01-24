package ru.popova.practice.shop.entity;

import lombok.*;

import java.io.Serializable;

/**
 * класс, представляюий собой составной ключ для топпинга для конкретного напитка в заказе
 */
@Getter
@Setter
@EqualsAndHashCode(of = {"drinkOrderId", "toppingId"})
@AllArgsConstructor
@NoArgsConstructor
public class ToppingForDrinkInOrderId implements Serializable {
    /**
     *id конкретного напитка для заказа
     */
    private Integer drinkOrderId;
    /**
     *id топпинга
     */
    private Integer toppingId;
}
