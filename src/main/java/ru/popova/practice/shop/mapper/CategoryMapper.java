package ru.popova.practice.shop.mapper;

import org.springframework.stereotype.Component;
import ru.popova.practice.shop.dto.CategoryDto;
import ru.popova.practice.shop.entity.CategoryEntity;

@Component
public class CategoryMapper implements AbstractMapper<CategoryEntity, CategoryDto> {

    @Override
    public CategoryDto toDto(CategoryEntity entity) {
        if (entity == null) {
            return null;
        }
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(entity.getId());
        categoryDto.setName(entity.getName());

        return categoryDto;
    }

    @Override
    public CategoryEntity toEntity(CategoryDto dto) {
        if (dto == null) {
            return null;
        }
        CategoryEntity category = new CategoryEntity();
        category.setId(dto.getId());
        category.setName(dto.getName());

        return category;
    }
}
