package ru.popova.practice.shop.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.popova.practice.shop.config.messages.MessageSourceDecorator;
import ru.popova.practice.shop.dto.NewDrinkDto;
import ru.popova.practice.shop.entity.CategoryEntity;
import ru.popova.practice.shop.entity.DrinkEntity;
import ru.popova.practice.shop.exception.NotFoundException;
import ru.popova.practice.shop.repository.CategoryEntityRepository;

import java.util.List;

import static ru.popova.practice.shop.util.constants.MessageConstants.CATEGORY_NOT_FOUND;

@Component
public class NewDrinkMapper implements AbstractMapper<DrinkEntity, NewDrinkDto> {

    private CategoryEntityRepository categoryEntityRepository;
    private MessageSourceDecorator messageSourceDecorator;

    @Autowired
    public NewDrinkMapper(CategoryEntityRepository categoryEntityRepository, MessageSourceDecorator messageSourceDecorator) {
        this.categoryEntityRepository = categoryEntityRepository;
        this.messageSourceDecorator = messageSourceDecorator;
    }

    @Override
    public NewDrinkDto toDto(DrinkEntity entity) {
        return null;
    }

    @Override
    public DrinkEntity toEntity(NewDrinkDto newDrinkDto) {
        if (newDrinkDto == null) {
            return null;
        }

        DrinkEntity drink = new DrinkEntity();
        drink.setName(newDrinkDto.getName());
        drink.setPrice(newDrinkDto.getPrice());
        drink.setVolume(newDrinkDto.getVolume());
        drink.setDescription(newDrinkDto.getDescription());
        List<Integer> categories = newDrinkDto.getCategories();

        for (Integer c : categories) {

            CategoryEntity category = categoryEntityRepository.findById(c)
                    .orElseThrow(() -> new NotFoundException("categories", messageSourceDecorator.getMessage(CATEGORY_NOT_FOUND)));

            drink.getCategories().add(category);
        }


        return drink;
    }
}
