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
        CategoryDto topping = new CategoryDto();
        topping.setId(entity.getId());
        topping.setName(entity.getName());

        return topping;
    }

    @Override
    public CategoryEntity toEntity(CategoryDto dto) {
        return null;
    }
}
