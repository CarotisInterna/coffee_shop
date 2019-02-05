package ru.popova.practice.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class NewDrinkDto {
    private String name;
    private BigDecimal price;
    private Integer volume;
    private List<String> images;
    private String description;
    private List<Integer> categories;
}
