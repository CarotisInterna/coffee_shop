package ru.popova.practice.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * информация о пользователе для аутентификации
 */
@Getter
@Setter
@NoArgsConstructor
public class AppUserLoginDto {
    Integer id;

    @NotNull
    @NotEmpty
    String username;

    @NotNull
    @NotEmpty
    String password;
}
