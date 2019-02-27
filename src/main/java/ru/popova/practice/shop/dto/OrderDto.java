package ru.popova.practice.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.popova.practice.shop.dto.groups.PlaceOrderGroup;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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

    @NotBlank(groups = PlaceOrderGroup.class)
    @Min(value = 10)
    @Max(value = 100)
    private String address;

    private LocalDateTime date;

    @NotEmpty(groups = PlaceOrderGroup.class)
    private List<DrinkOrderDto> drinks;
}
