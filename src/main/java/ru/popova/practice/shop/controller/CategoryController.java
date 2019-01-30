package ru.popova.practice.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.popova.practice.shop.dto.CategoryDto;
import ru.popova.practice.shop.dto.PageDto;
import ru.popova.practice.shop.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private CategoryService categoryService;

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


    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
}
