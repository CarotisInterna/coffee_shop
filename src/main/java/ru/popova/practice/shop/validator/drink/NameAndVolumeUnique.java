package ru.popova.practice.shop.validator.drink;

import org.springframework.beans.factory.annotation.Autowired;
import ru.popova.practice.shop.config.Messages;

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
