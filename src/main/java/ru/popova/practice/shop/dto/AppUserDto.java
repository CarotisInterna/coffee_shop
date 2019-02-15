package ru.popova.practice.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.popova.practice.shop.dto.groups.NotEmptyGroup;

import javax.validation.constraints.*;

/**
 * полная информация о пользователе
 */
@Getter
@Setter
@NoArgsConstructor
public class AppUserDto extends AppUserLoginDto {

    @NotBlank(groups = NotEmptyGroup.class)
    @Size(min = 3, max = 100)
    String fullName;

    @NotBlank(groups = NotEmptyGroup.class)
    @Pattern(regexp = "\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})([0-9]{4})", message = "{InvalidPhoneNumber.message}")
    String phoneNumber;

    @NotBlank(groups = NotEmptyGroup.class)
    String address;
}
