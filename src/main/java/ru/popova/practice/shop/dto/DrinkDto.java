package ru.popova.practice.shop.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DrinkDto {
    private Integer id;
    private String name;
    private BigDecimal price;
    private Integer volume;
    private List<String> images;
    private String description;
    private List<String> categories;
}
