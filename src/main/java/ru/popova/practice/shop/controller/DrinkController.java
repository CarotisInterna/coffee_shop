package ru.popova.practice.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.popova.practice.shop.dto.DrinkDto;
import ru.popova.practice.shop.dto.NewDrinkDto;
import ru.popova.practice.shop.dto.PageDto;
import ru.popova.practice.shop.exception.ValidationException;
import ru.popova.practice.shop.mapper.PageMapper;
import ru.popova.practice.shop.service.DrinkService;
import ru.popova.practice.shop.specification.DrinkSearchCriteria;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/api/drinks")
@RequiredArgsConstructor
public class DrinkController {

    private final DrinkService drinkService;
    private final PageMapper pageMapper;


    /**
     * получение списка напитков
     *
     * @param pageable
     * @return список напитков
     */
    @GetMapping
    public ResponseEntity<List<DrinkDto>> getDrinks(@PageableDefault Pageable pageable) {
        Page<DrinkDto> drinks = drinkService.getDrinks(pageable);
        return ResponseEntity.ok(drinks.getContent());
    }

    /**
     * метод для поиска напитков по параметрам
     *
     * @param name        имя
     * @param lowerPrice  нижняя граница цены
     * @param upperPrice  верхняя граница цены
     * @param lowerVolume нижняя граница объема
     * @param upperVolume верхняя граница объема
     * @param categoryId  идентификатор категории напитка
     * @param pageable
     * @return список напитков по параметрам
     */
    @GetMapping("/search")
    public ResponseEntity<PageDto<DrinkDto>> getDrinks(@RequestParam(value = "name", required = false) String name,
                                                       @RequestParam(value = "lower_price", required = false) BigDecimal lowerPrice,
                                                       @RequestParam(value = "upper_price", required = false) BigDecimal upperPrice,
                                                       @RequestParam(value = "lower_volume", required = false) Integer lowerVolume,
                                                       @RequestParam(value = "upper_volume", required = false) Integer upperVolume,
                                                       @RequestParam(value = "category_id", required = false) Integer categoryId,
                                                       @PageableDefault Pageable pageable) {
        DrinkSearchCriteria drinkSearchCriteria = DrinkSearchCriteria.builder()
                .name(name)
                .lowerPrice(lowerPrice)
                .upperPrice(upperPrice)
                .lowerVolume(lowerVolume)
                .upperVolume(upperVolume)
                .categoryId(categoryId)
                .build();
        return ResponseEntity.ok(drinkService.search(drinkSearchCriteria, pageable));
    }

    /**
     * получение списка напитков конкретной категории
     *
     * @param categoryId идентификатор категории
     * @return список напитков заданной категории
     */
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<List<DrinkDto>> getDrinksByCategory(@PathVariable("categoryId") Integer categoryId) {
        List<DrinkDto> drinks = drinkService.getDrinksByCategory(categoryId);
        return ResponseEntity.ok(drinks);
    }

    /**
     * получение напитка по id
     *
     * @param id идентификатор напитка
     * @return напиток или 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<DrinkDto> getDrinkById(@PathVariable Integer id) {
        return drinkService.getDrinkById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .notFound()
                        .build());
    }

    /**
     * сохранение напитка
     *
     * @param newDrinkDto новый напиток
     * @return сохраненный напиок
     */
    @PostMapping
    public ResponseEntity<DrinkDto> saveDrink(@RequestBody @Validated NewDrinkDto newDrinkDto, BindingResult result) {
        if (result.hasErrors()){
            throw new ValidationException(result);
        }
        return ResponseEntity.ok(drinkService.saveDrink(newDrinkDto));
    }
}
