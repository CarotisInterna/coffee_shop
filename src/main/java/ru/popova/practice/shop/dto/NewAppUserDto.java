package ru.popova.practice.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class NewAppUserDto extends AppUserDto {
    /**
     * подтверждение пароля при регистрации
     */
    @NotNull
    @NotEmpty
    String confirmPassword;
}
