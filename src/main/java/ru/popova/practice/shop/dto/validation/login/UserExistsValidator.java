package ru.popova.practice.shop.dto.validation.login;

import ru.popova.practice.shop.config.messages.Message;
import ru.popova.practice.shop.dto.AppUserLoginDto;
import ru.popova.practice.shop.service.AppUserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserExistsValidator implements ConstraintValidator<UserExists, AppUserLoginDto> {

    private UserExists userExists;

    private Message message;

    private AppUserService appUserService;

    public UserExistsValidator(Message message, AppUserService appUserService) {
        this.message = message;
        this.appUserService = appUserService;
    }

    @Override
    public void initialize(UserExists constraintAnnotation) {
        this.userExists = constraintAnnotation;
    }

    @Override
    public boolean isValid(AppUserLoginDto appUserLoginDto, ConstraintValidatorContext constraintValidatorContext) {
        String username = appUserLoginDto.getUsername();
        if(appUserService.getAppUserByUsername(username) == null){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    message.getMessage(userExists.message()))
                    .addPropertyNode("username")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
