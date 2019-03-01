package ru.popova.practice.shop.dto.validation.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static ru.popova.practice.shop.util.MessageConstants.USERNAME_UNIQUE;

@Documented
@Constraint(validatedBy = UsernameUniqueValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameUnique {

    String message() default USERNAME_UNIQUE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
