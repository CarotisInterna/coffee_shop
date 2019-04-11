package ru.popova.practice.shop.dto;

import lombok.*;
import ru.popova.practice.shop.dto.groups.NotEmptyGroup;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DrinkOrderDto {

    private Integer id;
    private DrinkDto drink;
    //    private OrderDto order;
    @NotNull(groups = NotEmptyGroup.class)
    @Min(value = 1)
    @Max(value = 50)
    private Integer quantity;
    private List<ToppingForDrinkInOrderDto> toppings;
}
