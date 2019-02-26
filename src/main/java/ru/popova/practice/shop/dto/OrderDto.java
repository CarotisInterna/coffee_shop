package ru.popova.practice.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {

    private Integer id;
    private String orderStatus;
    private String appUser;
    private BigDecimal total;
    private String address;
    private LocalDateTime date;
    private List<DrinkOrderDto> drinks;
}
