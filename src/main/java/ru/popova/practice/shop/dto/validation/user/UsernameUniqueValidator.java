package ru.popova.practice.shop.dto.validation.user;

import org.springframework.beans.factory.annotation.Autowired;
import ru.popova.practice.shop.config.messages.MessageSourceDecorator;
import ru.popova.practice.shop.dto.AppUserDto;
import ru.popova.practice.shop.dto.NewAppUserDto;
import ru.popova.practice.shop.service.AppUserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Валидатор уникальности имени пользователя
 */
public class UsernameUniqueValidator implements ConstraintValidator<UsernameUnique, NewAppUserDto> {

    private AppUserService appUserService;

    private UsernameUnique usernameUnique;

    private MessageSourceDecorator messageSourceDecorator;

    @Autowired
    public UsernameUniqueValidator(AppUserService appUserService, MessageSourceDecorator messageSourceDecorator) {
        this.appUserService = appUserService;
        this.messageSourceDecorator = messageSourceDecorator;
    }

    @Override
    public void initialize(UsernameUnique constraintAnnotation) {
        this.usernameUnique = constraintAnnotation;
    }

    /**
     * Процесс валидации имени пользователя
     *
     * @param newAppUserDto              дто нового пользователя
     * @param constraintValidatorContext контекст
     * @return false, если валидация не прошла, true иначе
     */
    @Override
    public boolean isValid(NewAppUserDto newAppUserDto, ConstraintValidatorContext constraintValidatorContext) {
        String username = newAppUserDto.getUsername();
        AppUserDto appUserDto = appUserService.getAppUserByUsername(username);
        if (appUserDto != null) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    messageSourceDecorator.getMessage(usernameUnique.message()))
                    .addPropertyNode("username")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
