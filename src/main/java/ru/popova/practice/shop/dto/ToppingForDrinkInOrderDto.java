package ru.popova.practice.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.popova.practice.shop.dto.groups.NotEmptyGroup;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ToppingForDrinkInOrderDto {

    private ToppingDto topping;

    @NotNull(groups = NotEmptyGroup.class)
    @Min(value = 1)
    @Max(value = 10)
    private Integer toppingAmount;
}
