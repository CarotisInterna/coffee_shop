package ru.popova.practice.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.popova.practice.shop.dto.groups.NotEmptyGroup;

import javax.validation.constraints.*;
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

    @NotBlank(groups = NotEmptyGroup.class)
    @Size(min = 10, max = 100)
    private String address;

    private LocalDateTime date;

    @NotEmpty(groups = NotEmptyGroup.class)
    private List<DrinkOrderDto> drinks;
}
