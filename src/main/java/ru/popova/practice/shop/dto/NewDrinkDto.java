package ru.popova.practice.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.popova.practice.shop.validator.drink.NameAndVolumeUnique;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@NameAndVolumeUnique
public class NewDrinkDto {

    @NotBlank//(message = "Наименование не может быть пустым или пробелом")
    @Size(min = 3, max = 20)
    private String name;

    @NotNull//(message = "Цена не может быть пустой")
    @Min(value = 30)
    @Max(value = 1000)
    private BigDecimal price;

    @NotNull//(message = "Объем не может быть пустым")
    @Min(value = 100)
    @Max(value = 1000)
    private Integer volume;

    @NotEmpty//(message = "Список картинок не может быть пустым")
    @Size(min = 1, max = 3)
    private List<@NotBlank String> images;

    @NotBlank//(message = "Описание не может быть пустым или пробелом")
    @Size(min = 10, max = 300)
    private String description;

    @NotEmpty//(message = "Список категорий не может быть пустым")
    @Size(min = 1, max = 5)
    private List<@NotNull Integer> categories;
}
