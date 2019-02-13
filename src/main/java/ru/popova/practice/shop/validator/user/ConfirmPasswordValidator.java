package ru.popova.practice.shop.validator.user;

import ru.popova.practice.shop.dto.NewAppUserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPassword, NewAppUserDto> {

    private ConfirmPassword confirmPassword;

    @Override
    public void initialize(ConfirmPassword constraintAnnotation) {
        this.confirmPassword = constraintAnnotation;
    }

    @Override
    public boolean isValid(NewAppUserDto newAppUserDto, ConstraintValidatorContext constraintValidatorContext) {
        if (!newAppUserDto.getPassword().equals(newAppUserDto.getConfirmPassword())){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    confirmPassword.message())
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
