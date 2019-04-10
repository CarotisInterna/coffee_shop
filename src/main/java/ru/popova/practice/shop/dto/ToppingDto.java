package ru.popova.practice.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.popova.practice.shop.dto.groups.NotEmptyGroup;

import javax.validation.constraints.*;
import java.math.BigDecimal;

import static ru.popova.practice.shop.util.constants.NumConstants.*;

@Getter
@Setter
@NoArgsConstructor
public class ToppingDto {
    private Integer id;

    @NotBlank(groups = NotEmptyGroup.class)
    @Size(min = MIN_NUMBER_OF_LETTERS_IN_TOPPING_NAME, max = MAX_NUMBER_OF_LETTERS_IN_TOPPING_NAME)
    @Pattern(regexp = "^[a-zA-ZА-Яа-я]+$", message = "{LetterString.message}")
    private String name;

    @NotNull(groups = NotEmptyGroup.class)
    @Min(value = MIN_TOPPING_PRICE)
    @Max(value = MAX_TOPPING_PRICE)
    private BigDecimal price;
}
