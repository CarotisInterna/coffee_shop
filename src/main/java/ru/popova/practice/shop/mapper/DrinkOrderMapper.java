package ru.popova.practice.shop.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.popova.practice.shop.dto.DrinkOrderDto;
import ru.popova.practice.shop.entity.DrinkOrderEntity;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DrinkOrderMapper implements AbstractMapper<DrinkOrderEntity, DrinkOrderDto> {

    private final DrinkMapper drinkMapper;
    private final OrderMapper orderMapper;
    private final ToppingForDrinkInOrderMapper toppingForDrinkInOrderMapper;

    @Override
    public DrinkOrderDto toDto(DrinkOrderEntity entity) {
        if (entity == null) {
            return null;
        } else {
            DrinkOrderDto drinkOrderDto = new DrinkOrderDto();
            drinkOrderDto.setDrink(drinkMapper.toDto(entity.getDrink()));
            drinkOrderDto.setOrder(orderMapper.toDto(entity.getOrder()));
            drinkOrderDto.setQuantity(entity.getQuantity());
            drinkOrderDto.setToppings(entity.getToppings()
                    .stream()
                    .map(toppingForDrinkInOrderMapper::toDto)
                    .collect(Collectors.toList()));
            return drinkOrderDto;
        }
    }

    @Override
    public DrinkOrderEntity toEntity(DrinkOrderDto dto) {
        if (dto == null) {
            return null;
        } else {
            DrinkOrderEntity drinkOrderEntity = new DrinkOrderEntity();
            drinkOrderEntity.setDrink(drinkMapper.toEntity(dto.getDrink()));
            drinkOrderEntity.setOrder(orderMapper.toEntity(dto.getOrder()));
            drinkOrderEntity.setQuantity(dto.getQuantity());
            drinkOrderEntity.setToppings(dto.getToppings()
                    .stream()
                    .map(toppingForDrinkInOrderMapper::toEntity)
                    .collect(Collectors.toList()));
            return drinkOrderEntity;
        }
    }
}
