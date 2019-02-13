package ru.popova.practice.shop.dto.validation.drink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import ru.popova.practice.shop.config.messages.Message;
import ru.popova.practice.shop.dto.DrinkDto;
import ru.popova.practice.shop.dto.NewDrinkDto;
import ru.popova.practice.shop.service.DrinkService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameAndVolumeUniqueValidator implements ConstraintValidator<NameAndVolumeUnique, NewDrinkDto> {

    private DrinkService drinkService;

    private NameAndVolumeUnique nameAndVolumeUnique;

    private Message message;

    @Autowired
    public NameAndVolumeUniqueValidator(DrinkService drinkService, MessageSource messageSource, Message message) {
        this.drinkService = drinkService;
        this.message = message;
    }

    @Override
    public void initialize(NameAndVolumeUnique constraintAnnotation) {
        this.nameAndVolumeUnique = constraintAnnotation;
    }

    @Override
    public boolean isValid(NewDrinkDto newDrinkDto, ConstraintValidatorContext constraintValidatorContext) {
        String name = newDrinkDto.getName();
        Integer volume = newDrinkDto.getVolume();
        DrinkDto drinkDto = drinkService.findDrinkByNameAndVolume(name, volume);
        if (drinkDto != null) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    message.getMessage(nameAndVolumeUnique.message()))
                    .addPropertyNode("name")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
