package ru.popova.practice.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.popova.practice.shop.dto.DrinkDto;
import ru.popova.practice.shop.dto.NewDrinkDto;
import ru.popova.practice.shop.entity.CategoryEntity;
import ru.popova.practice.shop.entity.DrinkEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;

public class TestUtils {

    /**
     * Создание корректного дто напитка для добавления
     *
     * @return дто нового напитка
     */
    static NewDrinkDto createBananaCocktail() {

        return createNewDrink("Банановый коктейль",
                150,
                300,
                "Десертный напиток на основе молока и мороженого.",
                2, 5
        );
    }


    static DrinkEntity createBananaCocktailEntity() {
        return createDrinkEntity("Банановый коктейль",
                150,
                300,
                "Десертный напиток на основе молока и мороженого.",
                2, 5
        );
    }

    static DrinkDto createBananaCocktailDto() {
        return createDrink(
                "Банановый коктейль",
                150,
                300,
                "Десертный напиток на основе молока и мороженого.",
                "test_category_" + 2, "test_category_" + 5
        );
    }

    private static DrinkEntity createDrinkEntity(String name, Integer price, Integer volume, String description, Integer... categories) {
        DrinkEntity drinkEntity = new DrinkEntity();

        drinkEntity.setName(name);
        drinkEntity.setPrice(BigDecimal.valueOf(price));
        drinkEntity.setVolume(volume);
        drinkEntity.setDescription(description);
        List<CategoryEntity> categoryEntities = new ArrayList<>();
        for (Integer category : categories) {
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setId(category);
            categoryEntity.setName("test_category_" + category);
            categoryEntities.add(categoryEntity);
        }
        drinkEntity.setCategories(categoryEntities);
        return drinkEntity;
    }


    static CategoryEntity getCategoryEntity(Integer category) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(category);
        categoryEntity.setName("test_category_" + category);
        return categoryEntity;
    }

    static DrinkEntity getDrinkEntity(Integer drink) {
        DrinkEntity drinkEntity = new DrinkEntity();
        drinkEntity.setId(drink);
        return drinkEntity;
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
    static NewDrinkDto createNewDrink(String name, Integer price, Integer volume, String description, Integer... categories) {

        return NewDrinkDto.builder()
                .name(name)
                .price(BigDecimal.valueOf(price))
                .volume(volume)
                .images(Collections.emptyList())
                .description(description)
                .categories(asList(categories))
                .build();
    }

    private static DrinkDto createDrink(String name, Integer price, Integer volume, String description, String... categories) {

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
