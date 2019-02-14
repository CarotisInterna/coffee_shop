package ru.popova.practice.shop.dto.validation.login;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserExistsValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserExists {

    String message() default "UserExists.message";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
