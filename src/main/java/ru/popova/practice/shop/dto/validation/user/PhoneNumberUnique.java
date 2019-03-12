package ru.popova.practice.shop.dto.validation.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static ru.popova.practice.shop.util.constants.MessageConstants.PHONE_NUMBER_UNIQUE;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumberUnique {

    String message() default PHONE_NUMBER_UNIQUE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
