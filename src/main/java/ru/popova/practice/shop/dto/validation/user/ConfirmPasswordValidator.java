package ru.popova.practice.shop.dto.validation.user;

import ru.popova.practice.shop.config.messages.Message;
import ru.popova.practice.shop.dto.NewAppUserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * Валидатор подтверждения пароля
 */
public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPassword, NewAppUserDto> {

    private ConfirmPassword confirmPassword;

    private Message message;

    public ConfirmPasswordValidator(Message message) {
        this.message = message;
    }

    @Override
    public void initialize(ConfirmPassword constraintAnnotation) {
        this.confirmPassword = constraintAnnotation;
    }

    /**
     * Процесс валидации подтверждения пароля
     *
     * @param newAppUserDto              дто нового пользователя
     * @param constraintValidatorContext контекст
     * @return false, если валидация не прошла, true иначе
     */
    @Override
    public boolean isValid(NewAppUserDto newAppUserDto, ConstraintValidatorContext constraintValidatorContext) {
        if (!newAppUserDto.getPassword().equals(newAppUserDto.getConfirmPassword())) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    message.getMessage(confirmPassword.message()))
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}