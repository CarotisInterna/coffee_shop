package ru.popova.practice.shop.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.popova.practice.shop.dto.ToppingForDrinkInOrderDto;
import ru.popova.practice.shop.entity.ToppingForDrinkInOrderEntity;

@Component
public class ToppingForDrinkInOrderMapper implements AbstractMapper<ToppingForDrinkInOrderEntity, ToppingForDrinkInOrderDto> {

    private ToppingMapper toppingMapper;

    @Override
    public ToppingForDrinkInOrderDto toDto(ToppingForDrinkInOrderEntity entity) {
        if (entity == null) {
            return null;
        } else {
            ToppingForDrinkInOrderDto toppingForDrinkInOrderDto = new ToppingForDrinkInOrderDto();
            toppingForDrinkInOrderDto.setTopping(toppingMapper.toDto(entity.getTopping()));
            toppingForDrinkInOrderDto.setToppingAmount(entity.getQuantity());
            return toppingForDrinkInOrderDto;
        }
    }

    @Override
    public ToppingForDrinkInOrderEntity toEntity(ToppingForDrinkInOrderDto dto) {
        return null;
    }

    @Autowired
    public void setToppingMapper(ToppingMapper toppingMapper) {
        this.toppingMapper = toppingMapper;
    }

}
