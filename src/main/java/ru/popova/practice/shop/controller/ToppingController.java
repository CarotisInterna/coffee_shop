package ru.popova.practice.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.popova.practice.shop.dto.PageDto;
import ru.popova.practice.shop.dto.ToppingDto;
import ru.popova.practice.shop.dto.groups.NotEmptyValidationSequence;
import ru.popova.practice.shop.service.ToppingService;

@RestController
@RequestMapping("/api/toppings")
public class ToppingController {

    private ToppingService toppingService;

    @Autowired
    public ToppingController(ToppingService toppingService) {
        this.toppingService = toppingService;
    }

    /**
     * Получение списка топпингов
     *
     * @param pageable
     * @return список топпингов
     */
    @GetMapping
    public ResponseEntity<PageDto<ToppingDto>> getToppings(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(toppingService.getToppings(pageable));
    }

    /**
     * Получение топпинга по id
     *
     * @param id идентификатор топпинга
     * @return топпинг или 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<ToppingDto> getToppingById(@PathVariable Integer id) {
        return toppingService.getToppingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .notFound()
                        .build());
    }

    /**
     * Сохранение топпинга
     *
     * @param toppingDto новый топпинг
     * @param result
     * @return статус
     */
    @PostMapping
    public ResponseEntity<ToppingDto> saveTopping(@RequestBody @Validated(NotEmptyValidationSequence.class) ToppingDto toppingDto, BindingResult result) {
        ToppingDto saved = toppingService.saveTopping(toppingDto, result);
        return ResponseEntity.ok(saved);
    }

    /**
     * Редактирование топпинга по id
     *
     * @param id         идентификатор топпинга
     * @param toppingDto дто топпинга
     * @param result
     * @return статус
     */
    @PutMapping("/{id}")
    public ResponseEntity<ToppingDto> editTopping(@PathVariable Integer id,
                                                  @RequestBody @Validated(NotEmptyValidationSequence.class) ToppingDto toppingDto,
                                                  BindingResult result) {
        ToppingDto edited = toppingService.editTopping(toppingDto, id, result);
        return ResponseEntity.ok(edited);
    }

    /**
     * Удаление топпинг а
     *
     * @param id идентификатор топпинга
     * @return статус
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteTopping(@PathVariable Integer id) {
        toppingService.deleteTopping(id);
        return ResponseEntity.ok().build();
    }

}
