package ru.popova.practice.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.popova.practice.shop.dto.validation.user.ConfirmPassword;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@ConfirmPassword
public class NewAppUserDto extends AppUserDto {
    /**
     * подтверждение пароля при регистрации
     */
    @NotBlank(message = "Пароль не может быть пустым или пробелом")
    @Size(min = 3, max = 20, message = "Пароль может быть от 3 до 20 символов")
    String confirmPassword;
}
