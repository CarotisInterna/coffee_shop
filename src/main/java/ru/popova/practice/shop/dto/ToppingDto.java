package ru.popova.practice.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.popova.practice.shop.dto.groups.NotEmptyGroup;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ToppingDto {
    private Integer id;

    @NotBlank(groups = NotEmptyGroup.class)
    @Size(min = 3, max = 40)
    private String name;

    @NotNull(groups = NotEmptyGroup.class)
    @Min(value = 0)
    @Max(value = 100)
    private BigDecimal price;
}
