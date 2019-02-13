package ru.popova.practice.shop.dto.validation.user;

import org.springframework.beans.factory.annotation.Autowired;
import ru.popova.practice.shop.config.messages.Message;
import ru.popova.practice.shop.dto.AppUserDto;
import ru.popova.practice.shop.dto.NewAppUserDto;
import ru.popova.practice.shop.service.AppUserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameUniqueValidator implements ConstraintValidator<UsernameUnique, NewAppUserDto> {

    private AppUserService appUserService;

    private UsernameUnique usernameUnique;

    private Message message;

    @Autowired
    public UsernameUniqueValidator(AppUserService appUserService, Message message) {
        this.appUserService = appUserService;
        this.message = message;
    }

    @Override
    public void initialize(UsernameUnique constraintAnnotation) {
        this.usernameUnique = constraintAnnotation;
    }

    @Override
    public boolean isValid(NewAppUserDto newAppUserDto, ConstraintValidatorContext constraintValidatorContext) {
        String username = newAppUserDto.getUsername();
        AppUserDto appUserDto = appUserService.getAppUserByUsername(username);
        if (appUserDto != null) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    message.getMessage(usernameUnique.message()))
                    .addPropertyNode("username")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
