package ru.popova.practice.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * информация о пользователе для аутентификации
 */
@Getter
@Setter
@NoArgsConstructor
public class AppUserLoginDto {
    Integer id;

    @NotBlank(message = "Имя пользователя не может быть пустым или пробелом")
    @Size(min = 3, max = 20, message = "Имя пользователя может быть от 3 до 20 символов")
    String username;

    @NotBlank(message = "Пароль не может быть пустым или пробелом")
    @Size(min = 3, max = 20, message = "Пароль может быть от 3 до 20 символов")
    String password;
}
