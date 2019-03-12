package ru.popova.practice.shop.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.popova.practice.shop.config.messages.MessageSourceDecorator;
import ru.popova.practice.shop.dto.DrinkDto;
import ru.popova.practice.shop.entity.CategoryEntity;
import ru.popova.practice.shop.entity.DrinkEntity;
import ru.popova.practice.shop.entity.DrinkImageEntity;
import ru.popova.practice.shop.exception.NotFoundException;
import ru.popova.practice.shop.repository.CategoryEntityRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.popova.practice.shop.util.constants.MessageConstants.CATEGORY_NOT_FOUND;

@Component
public class DrinkMapper implements AbstractMapper<DrinkEntity, DrinkDto> {

    @Value("${coffee_shop.images.path}")
    private String imagesPath;
    @Value("${coffee_shop.images.suffix}")
    private String imagesSuffix;

    private CategoryEntityRepository categoryEntityRepository;
    private MessageSourceDecorator messageSourceDecorator;

    @Autowired
    public DrinkMapper(CategoryEntityRepository categoryEntityRepository, MessageSourceDecorator messageSourceDecorator) {
        this.categoryEntityRepository = categoryEntityRepository;
        this.messageSourceDecorator = messageSourceDecorator;
    }

    @Override
    public DrinkDto toDto(DrinkEntity drinkEntity) {
        if (drinkEntity == null) {
            return null;
        }

        DrinkDto drink = new DrinkDto();
        drink.setId(drinkEntity.getId());
        drink.setName(drinkEntity.getName());
        drink.setPrice(drinkEntity.getPrice());
        drink.setVolume(drinkEntity.getVolume());
        List<String> images = drinkEntity.getImages()
                .stream()
                .map(DrinkImageEntity::getImage)
                .map(imageName -> imagesPath + imageName + imagesSuffix)
                .collect(Collectors.toList());
        drink.setImages(images);
        List<String> categories = drinkEntity.getCategories()
                .stream()
                .map(CategoryEntity::getName)
                .collect(Collectors.toList());
        drink.setCategories(categories);
        drink.setDescription(drinkEntity.getDescription());
        return drink;
    }

    @Override
    public DrinkEntity toEntity(DrinkDto drinkDto) {
        if (drinkDto == null) {
            return null;
        } else {
            DrinkEntity drink = new DrinkEntity();
            drink.setId(drinkDto.getId());
            drink.setName(drinkDto.getName());
            drink.setPrice(drinkDto.getPrice());
            drink.setVolume(drinkDto.getVolume());
            drink.setDescription(drinkDto.getDescription());
            List<String> categories = drinkDto.getCategories();

            for (String c : categories) {
                CategoryEntity category = categoryEntityRepository.findCategoryEntityByName(c);
                if (category == null) {
                    throw new NotFoundException("categories", messageSourceDecorator.getMessage(CATEGORY_NOT_FOUND));
                }

                drink.getCategories().add(category);
            }
            drink.setImages(drinkDto.getImages().stream()
                    .map(this::toDrinkImageEntity)
                    .peek(image -> image.setDrink(drink))
                    .collect(Collectors.toList()));
            return drink;
        }
    }

    private DrinkImageEntity toDrinkImageEntity(String image) {
        DrinkImageEntity drinkImageEntity = new DrinkImageEntity();
        drinkImageEntity.setImage(image);
        return drinkImageEntity;
    }
}
