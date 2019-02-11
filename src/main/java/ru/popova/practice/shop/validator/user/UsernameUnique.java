package ru.popova.practice.shop.validator.user;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameUniqueValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameUnique {

    String message() default "UsernameUnique.message";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
