package ru.popova.practice.shop.dto.validation.login;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.popova.practice.shop.config.messages.Message;
import ru.popova.practice.shop.dto.AppUserLoginDto;
import ru.popova.practice.shop.service.AppUserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, AppUserLoginDto> {

    private Password password;

    private Message message;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private AppUserService appUserService;

    public PasswordValidator(Message message, BCryptPasswordEncoder bCryptPasswordEncoder, AppUserService appUserService) {
        this.message = message;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.appUserService = appUserService;
    }

    @Override
    public void initialize(Password constraintAnnotation) {
        this.password = constraintAnnotation;
    }

    @Override
    public boolean isValid(AppUserLoginDto appUserLoginDto, ConstraintValidatorContext constraintValidatorContext) {
        String pass = appUserLoginDto.getPassword();
        String username = appUserLoginDto.getUsername();
        if (!bCryptPasswordEncoder.matches(pass, appUserService.getAppUserByUsername(username).getPassword())){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    message.getMessage(password.message()))
                    .addPropertyNode("password")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
