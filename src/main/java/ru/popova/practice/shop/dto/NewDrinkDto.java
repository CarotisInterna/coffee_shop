package ru.popova.practice.shop.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import ru.popova.practice.shop.dto.groups.NotEmptyGroup;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

import static ru.popova.practice.shop.util.constants.NumConstants.*;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class NewDrinkDto {

    @NotBlank(groups = NotEmptyGroup.class)
    @Size(min = MIN_NUMBER_OF_LETTERS_IN_DRINK_NAME, max = MAX_NUMBER_OF_LETTERS_IN_DRINK_NAME)
    @Pattern(regexp = "^[a-zA-ZА-Яа-я ]+$", message = "{AlphanumericString.message}")
    private String name;

    @NotNull(groups = NotEmptyGroup.class)
    @Min(value = MIN_DRINK_PRICE)
    @Max(value = MAX_DRINK_PRICE)
    private BigDecimal price;

    @NotNull(groups = NotEmptyGroup.class)
    @Min(value = MIN_DRINK_VOLUME)
    @Max(value = MAX_DRINK_VOLUME)
    private Integer volume;

    @Size(max = MAX_NUMBER_OF_IMAGES)
    private List<MultipartFile> images;

    @NotBlank(groups = NotEmptyGroup.class)
    @Size(min = MIN_NUMBER_OF_LETTERS_IN_DRINK_DESCRIPTION, max = MAX_NUMBER_OF_LETTERS_IN_DRINK_DESCRIPTION)
    @Pattern(regexp = "^[a-zA-ZА-Яа-я0-9 ]+$", message = "{LetterString.message}")
    private String description;

    @NotEmpty(groups = NotEmptyGroup.class)
    @Size(min = MIN_CATEGORIES_NUM, max = MAX_CATEGORIES_NUM)
    private List<@NotNull(groups = NotEmptyGroup.class) Integer> categories;
}
