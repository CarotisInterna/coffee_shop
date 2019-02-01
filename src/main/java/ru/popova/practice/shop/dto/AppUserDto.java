package ru.popova.practice.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * полная информация о пользователе
 */
@Getter
@Setter
@NoArgsConstructor
public class AppUserDto extends AppUserLoginDto {

    @NotNull
    @NotEmpty
    String fullName;

    @NotNull
    @NotEmpty
    String phoneNumber;

    @NotNull
    @NotEmpty
    String address;
}
