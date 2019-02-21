package ru.popova.practice.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DrinkOrderDto {
    private DrinkDto drink;
    private OrderDto order;
    private Integer quantity;
    private List<ToppingForDrinkInOrderDto> toppings;
}
