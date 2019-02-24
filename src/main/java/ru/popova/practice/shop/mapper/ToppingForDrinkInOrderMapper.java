package ru.popova.practice.shop.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.popova.practice.shop.dto.ToppingForDrinkInOrderDto;
import ru.popova.practice.shop.entity.ToppingForDrinkInOrderEntity;
import ru.popova.practice.shop.repository.ToppingEntityRepository;

@Component
@RequiredArgsConstructor
public class ToppingForDrinkInOrderMapper implements AbstractMapper<ToppingForDrinkInOrderEntity, ToppingForDrinkInOrderDto> {

    private final ToppingMapper toppingMapper;
    private final ToppingEntityRepository toppingEntityRepository;


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
        if (dto == null) {
            return null;
        } else {
            ToppingForDrinkInOrderEntity toppingForDrinkInOrderEntity = new ToppingForDrinkInOrderEntity();
            toppingForDrinkInOrderEntity.setQuantity(dto.getToppingAmount());
            toppingForDrinkInOrderEntity.setTopping(toppingEntityRepository.getOne(dto.getTopping().getId()));
            return toppingForDrinkInOrderEntity;
        }
    }

}
