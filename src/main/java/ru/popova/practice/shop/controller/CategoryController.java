package ru.popova.practice.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.popova.practice.shop.dto.CategoryDto;
import ru.popova.practice.shop.dto.ListErrorDto;
import ru.popova.practice.shop.dto.groups.NotEmptyValidationSequence;
import ru.popova.practice.shop.exception.ValidationException;
import ru.popova.practice.shop.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    /**
     * получение списка категорий
     *
     * @return список категорий
     */
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }

    /**
     * Получение категории по id
     *
     * @param id идентификатор категории
     * @return категория или 404
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer id) {
        return categoryService.getCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .notFound()
                        .build());
    }

    /**
     * Сохранение категории
     *
     * @param categoryDto новая категория
     * @param result
     * @return статус
     */
    @PostMapping
    public ResponseEntity<CategoryDto> saveCategory(@RequestBody @Validated(NotEmptyValidationSequence.class) CategoryDto categoryDto, BindingResult result) {
        ListErrorDto listErrorDto = categoryService.validateCategory(categoryDto);

        if (result.hasErrors() || !listErrorDto.getErrorDtos().isEmpty()) {
            throw new ValidationException(result, listErrorDto);
        }

        CategoryDto saved = categoryService.saveCategory(categoryDto);
        return ResponseEntity.ok(saved);
    }

    /**
     * Редактирование категории
     *
     * @param id          идентификатор категории
     * @param categoryDto изменения в категории
     * @param result
     * @return статус
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> editCategory(@PathVariable Integer id,
                                                    @RequestBody @Validated(NotEmptyValidationSequence.class) CategoryDto categoryDto,
                                                    BindingResult result) {
        ListErrorDto listErrorDto = categoryService.validateCategory(categoryDto);

        if (result.hasErrors() || !listErrorDto.getErrorDtos().isEmpty()) {
            throw new ValidationException(result, listErrorDto);
        }

        CategoryDto edited = categoryService.editCategory(categoryDto, id);
        return ResponseEntity.ok(edited);
    }

    /**
     * Удаление категории
     *
     * @param id идентификатор категории
     * @return статус
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }
}
