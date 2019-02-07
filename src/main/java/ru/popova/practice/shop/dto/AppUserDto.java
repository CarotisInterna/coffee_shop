package ru.popova.practice.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * полная информация о пользователе
 */
@Getter
@Setter
@NoArgsConstructor
public class AppUserDto extends AppUserLoginDto {

    @NotBlank(message = "Имя не может быть пустым или пробелом")
    @Size(min = 3, max = 100, message = "Полное имя должно быть от 3 до 100 символов")
    String fullName;

    @NotBlank(message = "Номер телефона не может быть пустым или пробелом")
    String phoneNumber;

    @NotBlank(message = "Адрес не может быть пустым или пробелом")
    String address;
}
