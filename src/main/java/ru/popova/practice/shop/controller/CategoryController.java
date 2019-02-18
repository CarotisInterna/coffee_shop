package ru.popova.practice.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.popova.practice.shop.dto.CategoryDto;
import ru.popova.practice.shop.dto.PageDto;
import ru.popova.practice.shop.dto.groups.NotEmptyValidationSequence;
import ru.popova.practice.shop.entity.CategoryEntity;
import ru.popova.practice.shop.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    /**
     * получение списка категорий
     *
     * @param pageable
     * @return список категорий
     */
    @GetMapping
    public ResponseEntity<PageDto<CategoryDto>> getCategories(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(categoryService.getCategories(pageable));
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
        CategoryDto saved = categoryService.saveCategory(categoryDto, result);
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
        CategoryDto edited = categoryService.editCategory(categoryDto, id, result);
        return ResponseEntity.ok(edited);
    }

    /** Удаление категории
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
