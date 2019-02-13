package ru.popova.practice.shop.dto.validation.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumberUnique {

    String message() default "PhoneNumberUnique.message";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
