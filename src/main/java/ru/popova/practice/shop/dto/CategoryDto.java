package ru.popova.practice.shop.dto;

import lombok.Getter;
import lombok.Setter;
import ru.popova.practice.shop.dto.groups.NotEmptyGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
public class CategoryDto {
    Integer id;

    @NotBlank(groups = NotEmptyGroup.class)
    @Size(min = 3, max = 20)
    String name;
}
