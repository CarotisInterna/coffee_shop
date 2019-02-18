package ru.popova.practice.shop.dto.validation.user;

import org.springframework.beans.factory.annotation.Autowired;
import ru.popova.practice.shop.config.messages.MessageSourceDecorator;
import ru.popova.practice.shop.dto.AppUserDto;
import ru.popova.practice.shop.dto.NewAppUserDto;
import ru.popova.practice.shop.service.AppUserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Валидатор уникальности номера телефона пользователя
 */
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberUnique, NewAppUserDto> {

    private AppUserService appUserService;

    private PhoneNumberUnique phoneNumberUnique;

    private MessageSourceDecorator messageSourceDecorator;

    @Autowired
    public PhoneNumberValidator(AppUserService appUserService, MessageSourceDecorator messageSourceDecorator) {
        this.appUserService = appUserService;
        this.messageSourceDecorator = messageSourceDecorator;
    }

    @Override
    public void initialize(PhoneNumberUnique constraintAnnotation) {
        this.phoneNumberUnique = constraintAnnotation;
    }

    /**
     * Процесс валидации уникальности номера телефона у пользователя
     *
     * @param newAppUserDto              дто нового пользователя
     * @param constraintValidatorContext контекст
     * @return false, если валидация не прошла, true иначе
     */
    @Override
    public boolean isValid(NewAppUserDto newAppUserDto, ConstraintValidatorContext constraintValidatorContext) {
        String phoneNumber = newAppUserDto.getPhoneNumber();
        AppUserDto appUserDto = appUserService.getAppUserByPhoneNumber(phoneNumber);
        if (appUserDto != null) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    messageSourceDecorator.getMessage(phoneNumberUnique.message()))
                    .addPropertyNode("phoneNumber")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
