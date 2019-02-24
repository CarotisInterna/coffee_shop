package ru.popova.practice.shop.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.popova.practice.shop.dto.DrinkOrderDto;
import ru.popova.practice.shop.entity.DrinkOrderEntity;
import ru.popova.practice.shop.entity.ToppingForDrinkInOrderId;
import ru.popova.practice.shop.repository.DrinkEntityRepository;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DrinkOrderMapper implements AbstractMapper<DrinkOrderEntity, DrinkOrderDto> {

    private final DrinkMapper drinkMapper;
    private final ToppingForDrinkInOrderMapper toppingForDrinkInOrderMapper;
    private final DrinkEntityRepository drinkEntityRepository;

    @Override
    public DrinkOrderDto toDto(DrinkOrderEntity entity) {
        if (entity == null) {
            return null;
        } else {
            DrinkOrderDto drinkOrderDto = new DrinkOrderDto();
            drinkOrderDto.setDrink(drinkMapper.toDto(entity.getDrink()));
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
            drinkOrderEntity.setDrink(drinkEntityRepository.getOne(dto.getDrink().getId()));
            drinkOrderEntity.setQuantity(dto.getQuantity());
            dto.getToppings()
                    .stream()
                    .map(toppingForDrinkInOrderMapper::toEntity)
                    .peek(topping -> {
                        ToppingForDrinkInOrderId id = new ToppingForDrinkInOrderId(
                                drinkOrderEntity.getId(),
                                topping.getTopping().getId()
                        );
                        topping.setId(id);
                    })
                    .forEach(drinkOrderEntity::addTopping);
            return drinkOrderEntity;
        }
    }
}
