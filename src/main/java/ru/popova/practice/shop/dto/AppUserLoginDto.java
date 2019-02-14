package ru.popova.practice.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.popova.practice.shop.dto.groups.LoginGroup;
import ru.popova.practice.shop.dto.groups.NotEmptyGroup;
import ru.popova.practice.shop.dto.groups.RegisterGroup;
import ru.popova.practice.shop.dto.validation.login.Password;
import ru.popova.practice.shop.dto.validation.login.UserExists;

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
@Password
@UserExists(groups = LoginGroup.class)
public class AppUserLoginDto {
    Integer id;

    @NotBlank(groups = NotEmptyGroup.class)
    @Size(min = 3, max = 20, groups = RegisterGroup.class)
    String username;

    @NotBlank(groups = NotEmptyGroup.class)
    @Size(min = 3, max = 20, groups = RegisterGroup.class)
    String password;
}
