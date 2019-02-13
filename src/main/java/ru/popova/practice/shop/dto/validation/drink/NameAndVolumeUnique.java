package ru.popova.practice.shop.dto.validation.drink;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NameAndVolumeUniqueValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NameAndVolumeUnique {

    String message() default "NameAndVolumeUnique.message";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
