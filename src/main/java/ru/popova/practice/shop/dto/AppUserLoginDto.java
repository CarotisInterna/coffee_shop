package ru.popova.practice.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.popova.practice.shop.dto.groups.NotEmptyGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * информация о пользователе для аутентификации
 */
@Getter
@Setter
@NoArgsConstructor
public class AppUserLoginDto {
    Integer id;

    @NotBlank(groups = NotEmptyGroup.class)
    @Size(min = 3, max = 20)
    String username;

    @NotBlank(groups = NotEmptyGroup.class)
    @Size(min = 3, max = 20)
    String password;
}
