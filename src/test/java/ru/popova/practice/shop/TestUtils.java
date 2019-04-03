package ru.popova.practice.shop;

import ru.popova.practice.shop.dto.DrinkDto;
import ru.popova.practice.shop.dto.NewDrinkDto;
import ru.popova.practice.shop.entity.CategoryEntity;
import ru.popova.practice.shop.entity.DrinkEntity;

import java.math.BigDecimal;
import java.util.Collections;

import static java.util.Arrays.asList;

public class TestUtils {

    /**
     * Создание корректного дто напитка для добавления
     *
     * @return дто нового напитка
     */
    public static NewDrinkDto createBananaCocktail() {

        return createNewDrink("Банановый коктейль",
                150,
                300,
                "Десертный напиток на основе молока и мороженого.",
                2, 5
        );
    }


    public static DrinkEntity createBananaCocktailEntity() {
        return createDrinkEntity("Банановый коктейль",
                150,
                300,
                "Десертный напиток на основе молока и мороженого.",
                2, 5
        );
    }

    public static DrinkDto createBananaCocktailDto() {
        return createDrink(
                "Банановый коктейль",
                150,
                300,
                "Десертный напиток на основе молока и мороженого.",
                "test_category_" + 2, "test_category_" + 5
        );
    }

    public static DrinkEntity createDrinkEntity(String name, Integer price, Integer volume, String description, Integer... categories) {
        DrinkEntity drinkEntity = new DrinkEntity();

        drinkEntity.setName(name);
        drinkEntity.setPrice(BigDecimal.valueOf(price));
        drinkEntity.setVolume(volume);
        drinkEntity.setDescription(description);
        for (Integer category : categories) {
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setId(category);
            categoryEntity.setName("test_category_" + category);
        }
        return drinkEntity;
    }


    public static CategoryEntity getCategoryEntity(Integer category) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(category);
        categoryEntity.setName("test_category_" + category);
        return categoryEntity;
    }

    /**
     * Создание напитка
     *
     * @param name        наименование
     * @param price       цена
     * @param volume      объем
     * @param description описание
     * @param categories  категории напитка
     * @return дто нового напитка
     */
    public static NewDrinkDto createNewDrink(String name, Integer price, Integer volume, String description, Integer... categories) {

        return NewDrinkDto.builder()
                .name(name)
                .price(BigDecimal.valueOf(price))
                .volume(volume)
                .images(Collections.emptyList())
                .description(description)
                .categories(asList(categories))
                .build();
    }

    public static DrinkDto createDrink(String name, Integer price, Integer volume, String description, String... categories) {

        return DrinkDto.builder()
                .name(name)
                .price(BigDecimal.valueOf(price))
                .volume(volume)
                .images(Collections.emptyList())
                .description(description)
                .categories(asList(categories))
                .build();
    }
}
