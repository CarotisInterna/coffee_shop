package ru.popova.practice.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.popova.practice.shop.dto.ListErrorDto;
import ru.popova.practice.shop.dto.PageDto;
import ru.popova.practice.shop.dto.ToppingDto;
import ru.popova.practice.shop.dto.groups.NotEmptyValidationSequence;
import ru.popova.practice.shop.exception.ValidationException;
import ru.popova.practice.shop.service.ToppingService;

import java.util.List;

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
     * @return список топпингов
     */
    @GetMapping
    public ResponseEntity<List<ToppingDto>> getToppings() {
        return ResponseEntity.ok(toppingService.getToppings());
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
    public ResponseEntity<ToppingDto> saveTopping(@ModelAttribute("topping") @Validated(NotEmptyValidationSequence.class) ToppingDto toppingDto, BindingResult result) {

        ListErrorDto listErrorDto = toppingService.validateTopping(toppingDto);

        if (result.hasErrors() || !listErrorDto.getErrorDtos().isEmpty()) {
            throw new ValidationException(result, listErrorDto);
        }
        ToppingDto saved = toppingService.saveTopping(toppingDto);
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
                                                  @ModelAttribute @Validated(NotEmptyValidationSequence.class) ToppingDto toppingDto,
                                                  BindingResult result) {

        ListErrorDto listErrorDto = toppingService.validateTopping(toppingDto);

        if (result.hasErrors() || listErrorDto.getErrorDtos().isEmpty()) {
            throw new ValidationException(result, listErrorDto);
        }

        ToppingDto edited = toppingService.editTopping(toppingDto, id);
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
