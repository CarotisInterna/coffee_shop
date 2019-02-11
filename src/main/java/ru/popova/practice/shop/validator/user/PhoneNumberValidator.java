package ru.popova.practice.shop.validator.user;

import org.springframework.beans.factory.annotation.Autowired;
import ru.popova.practice.shop.dto.AppUserDto;
import ru.popova.practice.shop.dto.NewAppUserDto;
import ru.popova.practice.shop.service.AppUserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberUnique, NewAppUserDto> {

    private AppUserService appUserService;

    private PhoneNumberUnique phoneNumberUnique;

    @Autowired
    public PhoneNumberValidator(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Override
    public void initialize(PhoneNumberUnique constraintAnnotation) {
        this.phoneNumberUnique = constraintAnnotation;
    }

    @Override
    public boolean isValid(NewAppUserDto newAppUserDto, ConstraintValidatorContext constraintValidatorContext) {
        String phoneNumber = newAppUserDto.getPhoneNumber();
        AppUserDto appUserDto = appUserService.getAppUserByPhoneNumber(phoneNumber);
        if (appUserDto != null) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    phoneNumberUnique.message())
                    .addPropertyNode("phoneNumber")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
