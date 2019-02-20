package ru.popova.practice.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ToppingForDrinkInOrderDto {
    private ToppingDto topping;
    private Integer toppingAmount;
}
