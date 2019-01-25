package ru.popova.practice.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.popova.practice.shop.entity.DrinkEntity;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class DrinkDto {
    private String name;
    private BigDecimal price;
    private Integer volume;
    private String description;
}
