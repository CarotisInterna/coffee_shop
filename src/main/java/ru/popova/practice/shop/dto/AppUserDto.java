package ru.popova.practice.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.popova.practice.shop.dto.groups.NotEmptyGroup;

import javax.validation.constraints.*;

import static ru.popova.practice.shop.util.constants.NumConstants.*;

/**
 * полная информация о пользователе
 */
@Getter
@Setter
@NoArgsConstructor
public class AppUserDto extends AppUserLoginDto {

    @NotBlank(groups = NotEmptyGroup.class)
    @Size(min = MIN_FULL_NAME_LENGTH, max = MAX_FULL_NAME_LENGTH)
    String fullName;

    @NotBlank(groups = NotEmptyGroup.class)
    @Pattern(regexp = "^[0-9]+$", message = "{InvalidPhoneNumber.message}")
    @Size(min = MIN_PHONE_NUMBER_LENGTH, max = MAX_PHONE_NUMBER_LENGTH)
    String phoneNumber;

    @NotBlank(groups = NotEmptyGroup.class)
    String address;
}
