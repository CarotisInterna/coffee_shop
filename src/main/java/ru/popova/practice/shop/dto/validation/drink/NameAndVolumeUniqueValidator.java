package ru.popova.practice.shop.dto.validation.drink;

import org.springframework.beans.factory.annotation.Autowired;
import ru.popova.practice.shop.config.messages.MessageSourceDecorator;
import ru.popova.practice.shop.dto.DrinkDto;
import ru.popova.practice.shop.dto.NewDrinkDto;
import ru.popova.practice.shop.service.DrinkService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Валидатор уникальности сочетания наименования и объема напитка
 */
public class NameAndVolumeUniqueValidator implements ConstraintValidator<NameAndVolumeUnique, NewDrinkDto> {

    private DrinkService drinkService;

    private NameAndVolumeUnique nameAndVolumeUnique;

    private MessageSourceDecorator messageSourceDecorator;

    @Autowired
    public NameAndVolumeUniqueValidator(DrinkService drinkService, MessageSourceDecorator messageSourceDecorator) {
        this.drinkService = drinkService;
        this.messageSourceDecorator = messageSourceDecorator;
    }

    @Override
    public void initialize(NameAndVolumeUnique constraintAnnotation) {
        this.nameAndVolumeUnique = constraintAnnotation;
    }

    /**
     * Процесс валидации уникальности сочетания наименования и объема напитка
     *
     * @param newDrinkDto                дто нового напитка
     * @param constraintValidatorContext контекст
     * @return false, если валидация не прошла, true иначе
     */
    @Override
    public boolean isValid(NewDrinkDto newDrinkDto, ConstraintValidatorContext constraintValidatorContext) {
        String name = newDrinkDto.getName();
        Integer volume = newDrinkDto.getVolume();
        DrinkDto drinkDto = drinkService.findDrinkByNameAndVolume(name, volume);
        if (drinkDto != null) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    messageSourceDecorator.getMessage(nameAndVolumeUnique.message()))
                    .addPropertyNode("name")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
