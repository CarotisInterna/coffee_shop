package ru.popova.practice.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.popova.practice.shop.dto.DrinkDto;
import ru.popova.practice.shop.service.DrinkService;

import java.util.List;


@RestController
@RequestMapping("/api/drinks")
public class DrinkController {

    private DrinkService drinkService;

    /**
     * получение списка напитков
     * @param pageable
     * @return список напитков
     */
    @GetMapping
    public ResponseEntity<List<DrinkDto>> getDrinks(@PageableDefault Pageable pageable) {
        Page<DrinkDto> drinks = drinkService.getDrinks(pageable);
        return ResponseEntity.ok(drinks.getContent());
    }

    /**
     * получение списка напитков конкретной категории
     * @param categoryId идентификатор категории
     * @return список напитков заданной категории
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<List<DrinkDto>> getDrinksByCategory(@PathVariable Integer categoryId) {
        List<DrinkDto> drinks = drinkService.getDrinksByCategory(categoryId);
        return ResponseEntity.ok(drinks);
    }

    /**
     * получение напитка по id
     * @param id идентификатор напитка
     * @return напиток или 404
     */
    @GetMapping("/{categoryId}/{id}")
    public ResponseEntity<DrinkDto> getDrinkById(@PathVariable Integer id) {
        return drinkService.getDrinkById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .notFound()
                        .build());
    }

    @Autowired
    public DrinkController(DrinkService drinkService) {
        this.drinkService = drinkService;
    }
}
