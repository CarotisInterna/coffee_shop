package ru.popova.practice.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.popova.practice.shop.dto.groups.NotEmptyGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class NewAppUserDto extends AppUserDto {
    /**
     * подтверждение пароля при регистрации
     */
    @NotBlank(groups = NotEmptyGroup.class)
    @Size(min = 3, max = 20)
    String confirmPassword;
}
