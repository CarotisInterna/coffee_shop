package ru.popova.practice.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import ru.popova.practice.shop.dto.groups.NotEmptyGroup;
import ru.popova.practice.shop.dto.validation.drink.NameAndVolumeUnique;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@NameAndVolumeUnique
@AllArgsConstructor
public class NewDrinkDto {

    @NotBlank(groups = NotEmptyGroup.class)
    @Size(min = 3, max = 20)
    private String name;

    @NotNull(groups = NotEmptyGroup.class)
    @Min(value = 30)
    @Max(value = 1000)
    private BigDecimal price;

    @NotNull(groups = NotEmptyGroup.class)
    @Min(value = 100)
    @Max(value = 1000)
    private Integer volume;

    @Size(max = 3)
    private List<MultipartFile> images;

    @NotBlank(groups = NotEmptyGroup.class)
    @Size(min = 10, max = 300)
    private String description;

    @NotEmpty(groups = NotEmptyGroup.class)
    @Size(min = 1, max = 5)
    private List<@NotNull(groups = NotEmptyGroup.class) Integer> categories;
}
