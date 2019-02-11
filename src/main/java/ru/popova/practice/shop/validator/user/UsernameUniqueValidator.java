package ru.popova.practice.shop.validator.user;

import org.springframework.beans.factory.annotation.Autowired;
import ru.popova.practice.shop.dto.AppUserDto;
import ru.popova.practice.shop.dto.NewAppUserDto;
import ru.popova.practice.shop.service.AppUserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameUniqueValidator implements ConstraintValidator<UsernameUnique, NewAppUserDto> {

    private AppUserService appUserService;

    private UsernameUnique usernameUnique;

    @Autowired
    public UsernameUniqueValidator(AppUserService appUserService) {
        this.appUserService = appUserService;
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
                    usernameUnique.message())
                    .addPropertyNode("username")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
