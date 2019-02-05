package ru.popova.practice.shop.mapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.popova.practice.shop.dto.DrinkDto;
import ru.popova.practice.shop.entity.CategoryEntity;
import ru.popova.practice.shop.entity.DrinkEntity;
import ru.popova.practice.shop.entity.DrinkImageEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DrinkMapper implements AbstractMapper<DrinkEntity, DrinkDto> {

    @Value("${coffee_shop.images.path}")
    private String imagesPath;
    @Value("${coffee_shop.images.suffix}")
    private String imagesSuffix;

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
        List<DrinkImageEntity> drinkImages = drinkEntity.getImages();
        drink.setImages(drinkImages.isEmpty() ? null :
                drinkImages.stream()
                        .map(DrinkImageEntity::getImage)
                        .map(imageName -> imagesPath + imageName + imagesSuffix)
                        .collect(Collectors.toList()));
        List<CategoryEntity> categories = drinkEntity.getCategories();
        drink.setCategories(categories.isEmpty() ? null :
                categories.stream()
                        .map(CategoryEntity::getName)
                        .collect(Collectors.toList()));
        drink.setDescription(drinkEntity.getDescription());
        return drink;
    }

    @Override
    public DrinkEntity toEntity(DrinkDto drinkDto) {
        if (drinkDto == null) {
            return null;
        }
        DrinkEntity drink = new DrinkEntity();
        drink.setName(drinkDto.getName());
        drink.setPrice(drinkDto.getPrice());
        drink.setVolume(drinkDto.getVolume());
        List<String> images = drinkDto.getImages();
    }

    public DrinkImageEntity toEntity()

}
