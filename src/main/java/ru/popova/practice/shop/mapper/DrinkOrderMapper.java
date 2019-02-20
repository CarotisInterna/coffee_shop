package ru.popova.practice.shop.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.popova.practice.shop.dto.DrinkOrderDto;
import ru.popova.practice.shop.dto.ToppingForDrinkInOrderDto;
import ru.popova.practice.shop.entity.DrinkOrderEntity;
import ru.popova.practice.shop.entity.ToppingForDrinkInOrderEntity;

import java.util.stream.Collectors;

@Component
public class DrinkOrderMapper implements AbstractMapper<DrinkOrderEntity, DrinkOrderDto> {

    private DrinkMapper drinkMapper;
    private ToppingForDrinkInOrderMapper toppingForDrinkInOrderMapper;

    @Override
    public DrinkOrderDto toDto(DrinkOrderEntity entity) {
        if (entity == null) {
            return null;
        } else {
            DrinkOrderDto drinkOrderDto = new DrinkOrderDto();
            drinkOrderDto.setDrink(drinkMapper.toDto(entity.getDrink()));
            drinkOrderDto.setDrinkAmount(entity.getQuantity());
            drinkOrderDto.setToppings(entity.getToppings()
                    .stream()
                    .map(topping -> toppingForDrinkInOrderMapper.toDto(topping))
                    .collect(Collectors.toList()));
            return drinkOrderDto;
        }
    }

    @Override
    public DrinkOrderEntity toEntity(DrinkOrderDto dto) {
        return null;
    }

    @Autowired
    public void setDrinkMapper(DrinkMapper drinkMapper) {
        this.drinkMapper = drinkMapper;
    }
}
