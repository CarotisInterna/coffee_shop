package ru.popova.practice.shop.dto.validation.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ConfirmPasswordValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfirmPassword {

    String message() default "ConfirmPassword.message";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
