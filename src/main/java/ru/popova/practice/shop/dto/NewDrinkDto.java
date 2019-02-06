package ru.popova.practice.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class NewDrinkDto {

    @NotBlank(message = "Наименование не может быть пустым или пробелом")
    @Size(min = 3, max =  20, message = "Наименование должно быть от 3 до 20 символов")
    private String name;

    @NotNull(message = "Цена не может быть пустой")
    @Min(value = 30, message = "Цена не может быть меньше 30")
    @Max(value = 1000, message = "Цена не может быть больше 1000")
    private BigDecimal price;

    @NotNull(message = "Объем не может быть пустым")
    @Min(value = 100, message = "Объем не может быть меньше 100")
    @Max(value = 1000, message = "Объем не может быть больше 1000")
    private Integer volume;

    @NotEmpty(message = "Список картинок не может быть пустым")
    @Size(min = 1, max = 3, message = "Картинок может быть от 1 до 3")
    private List<@NotBlank(message = "Картинка не может быть пустой или пробелом") String> images;

    @NotBlank(message = "Описание не может быть пустым или пробелом")
    @Size(min = 10, max =  300, message = "Описание должно быть от 3 до 20 символов")
    private String description;

    @NotEmpty(message = "Список категорий не может быть пустым")
    @Size(min = 1, max = 5, message = "Категорий может быть от 1 до 5")
    private List<@NotNull(message = "Категория не может быть пустой") Integer> categories;
}
