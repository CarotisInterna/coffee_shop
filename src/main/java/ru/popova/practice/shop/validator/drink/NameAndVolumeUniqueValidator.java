package ru.popova.practice.shop.validator.drink;

import org.springframework.beans.factory.annotation.Autowired;
import ru.popova.practice.shop.dto.DrinkDto;
import ru.popova.practice.shop.dto.NewDrinkDto;
import ru.popova.practice.shop.service.DrinkService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameAndVolumeUniqueValidator implements ConstraintValidator<NameAndVolumeUnique, NewDrinkDto> {

    private DrinkService drinkService;

    @Autowired
    public NameAndVolumeUniqueValidator(DrinkService drinkService) {
        this.drinkService = drinkService;
    }

    @Override
    public void initialize(NameAndVolumeUnique constraintAnnotation) {

    }

    @Override
    public boolean isValid(NewDrinkDto newDrinkDto, ConstraintValidatorContext constraintValidatorContext) {
        String name = newDrinkDto.getName();
        Integer volume = newDrinkDto.getVolume();
        DrinkDto drinkDto = drinkService.findDrinkByNameAndVolume(name, volume);
        if (drinkDto != null) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    "{ru.popova.practice.shop.validation.constraints.NameAndVolumeUnique.message}")
                    .addPropertyNode("name")
                    .addConstraintViolation();
           return false;
        }
        return true;
    }
}
