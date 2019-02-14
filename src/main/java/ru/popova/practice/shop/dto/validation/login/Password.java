package ru.popova.practice.shop.dto.validation.login;

import ru.popova.practice.shop.dto.validation.user.ConfirmPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    String message() default "Password.message";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
