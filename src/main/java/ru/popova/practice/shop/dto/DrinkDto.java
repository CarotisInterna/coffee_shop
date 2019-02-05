package ru.popova.practice.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.popova.practice.shop.entity.DrinkEntity;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DrinkDto {
    private Integer id;
    private String name;
    private BigDecimal price;
    private Integer volume;
    private List<String> images;
    private String description;
    private List<String> categories;
}
