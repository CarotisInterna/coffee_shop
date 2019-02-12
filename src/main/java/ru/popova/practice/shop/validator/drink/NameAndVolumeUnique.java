package ru.popova.practice.shop.validator.drink;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameAndVolumeUniqueValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NameAndVolumeUnique {

    String message() default "ru.popova.practice.shop.validation.constraints.NameAndVolumeUnique.message";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
