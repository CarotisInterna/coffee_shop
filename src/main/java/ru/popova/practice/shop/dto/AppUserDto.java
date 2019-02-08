package ru.popova.practice.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

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
    @Pattern(regexp = "\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})([0-9]{4})", message = "Неверный формат ввода номера телефона")
    String phoneNumber;

    @NotBlank(message = "Адрес не может быть пустым или пробелом")
    String address;
}
