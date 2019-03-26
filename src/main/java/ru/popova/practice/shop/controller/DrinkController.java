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
import ru.popova.practice.shop.dto.groups.NotEmptyValidationSequence;
import ru.popova.practice.shop.exception.ValidationException;
import ru.popova.practice.shop.service.DrinkService;
import ru.popova.practice.shop.specification.DrinkSearchCriteria;

import java.math.BigDecimal;
import java.util.List;

import static ru.popova.practice.shop.util.constants.NumConstants.NUMBER_OF_ELEMENTS_ON_PAGE;


@RestController
@RequestMapping("/api/drinks")
@RequiredArgsConstructor
public class DrinkController {

    private final DrinkService drinkService;


    /**
     * Получение списка напитков
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
     * Метод для поиска напитков по параметрам
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
                                                       @PageableDefault(size = NUMBER_OF_ELEMENTS_ON_PAGE) Pageable pageable) {
        return getPageDtoResponseEntity(name, lowerPrice, upperPrice, lowerVolume, upperVolume, categoryId, pageable);
    }

    //TODO: нужен ли этот метод? getDrinks выполняет ту же функцию
    /**
     * Получение списка напитков конкретной категории
     *
     * @param categoryId идентификатор категории
     * @return список напитков заданной категории
     */
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<PageDto<DrinkDto>> getDrinksByCategory(@PathVariable("categoryId") Integer categoryId,
                                                                 @RequestParam(value = "name", required = false) String name,
                                                                 @RequestParam(value = "lower_price", required = false) BigDecimal lowerPrice,
                                                                 @RequestParam(value = "upper_price", required = false) BigDecimal upperPrice,
                                                                 @RequestParam(value = "lower_volume", required = false) Integer lowerVolume,
                                                                 @RequestParam(value = "upper_volume", required = false) Integer upperVolume,
                                                                 @PageableDefault(size = NUMBER_OF_ELEMENTS_ON_PAGE) Pageable pageable) {
        return getPageDtoResponseEntity(name, lowerPrice, upperPrice, lowerVolume, upperVolume, categoryId, pageable);
    }

    /**
     * Получение напитка по id
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
     * Сохранение напитка
     *
     * @param newDrinkDto новый напиток
     * @return статус
     */
    @PostMapping
    public ResponseEntity<DrinkDto> saveDrink(@ModelAttribute("drink") @Validated(NotEmptyValidationSequence.class) NewDrinkDto newDrinkDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }

        DrinkDto saved = drinkService.editDrink(newDrinkDto);
        return ResponseEntity.ok(saved);
    }

    /**
     * Редактирование напитка
     *
     * @param id          идентификатор напитка
     * @param editDrinkDto изменения в напитке
     * @param result
     * @return статус
     */
    @PutMapping("/{id}")
    public ResponseEntity<DrinkDto> editDrink(@PathVariable Integer id,
                                              @ModelAttribute @Validated(NotEmptyValidationSequence.class) NewDrinkDto editDrinkDto,
                                              BindingResult result) {
        //TODO: name приходит null, не заполняется с задисейбленным инпутом
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }
        DrinkDto edited = drinkService.editDrink(editDrinkDto, id);
        return ResponseEntity.ok(edited);
    }

    /**
     * Удаление напитка
     *
     * @param id идентификатор напитка
     * @return статус
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteDrink(@PathVariable Integer id) {
        drinkService.deleteDrink(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Поиск напитков по параметров для конкретных категорий
     *
     * @param name        имя
     * @param lowerPrice  нижняя граница цены
     * @param upperPrice  верхняя граница цены
     * @param lowerVolume нижняя граница объема
     * @param upperVolume верхняя граница объема
     * @param categoryId  идентификатор категории напитка
     * @param pageable
     * @return страница со списком напитков
     */
    private ResponseEntity<PageDto<DrinkDto>> getPageDtoResponseEntity(String name, BigDecimal lowerPrice, BigDecimal upperPrice, Integer lowerVolume, Integer upperVolume, Integer categoryId, Pageable pageable) {
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
}
