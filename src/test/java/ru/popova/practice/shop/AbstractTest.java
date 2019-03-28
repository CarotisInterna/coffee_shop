package ru.popova.practice.shop;

import ru.popova.practice.shop.dto.NewDrinkDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractTest {

    /**
     * Создание корректного дто напитка для добавления
     *
     * @return дто нового напитка
     */
    public NewDrinkDto createBananaCocktail() {

        return createNewDrink("Банановый коктейль",
                150,
                300,
                "Десертный напиток на основе молока и мороженого.",
                2, 5
        );
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
    public NewDrinkDto createNewDrink(String name, Integer price, Integer volume, String description, Integer... categories) {

        List<Integer> categoriesList = new ArrayList<>(Arrays.asList(categories));

        return NewDrinkDto.builder()
                .name(name)
                .price(new BigDecimal(price))
                .volume(volume)
                .images(Collections.emptyList())
                .description(description)
                .categories(categoriesList)
                .build();
    }
}
