package ru.popova.practice.shop.mapper;

import org.springframework.stereotype.Component;
import ru.popova.practice.shop.dto.ToppingDto;
import ru.popova.practice.shop.entity.ToppingEntity;

@Component
public class ToppingMapper implements AbstractMapper<ToppingEntity, ToppingDto> {

    @Override
    public ToppingDto toDto(ToppingEntity entity) {
        if (entity == null) {
            return null;
        }
        ToppingDto topping = new ToppingDto();
        topping.setId(entity.getId());
        topping.setName(entity.getName());
        topping.setPrice(entity.getPrice());

        return topping;
    }

    @Override
    public ToppingEntity toEntity(ToppingDto dto) {
        if (dto == null) {
            return null;
        }
        ToppingEntity topping = new ToppingEntity();
        topping.setId(dto.getId());
        topping.setName(dto.getName());
        topping.setPrice(dto.getPrice());

        return topping;
    }
}
