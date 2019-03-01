package ru.popova.practice.shop.dto.validation.drink;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static ru.popova.practice.shop.util.MessageConstants.NAME_AND_VOLUME_UNIQUE;

@Documented
@Constraint(validatedBy = NameAndVolumeUniqueValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NameAndVolumeUnique {

    String message() default NAME_AND_VOLUME_UNIQUE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
