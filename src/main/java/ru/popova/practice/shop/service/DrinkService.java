package ru.popova.practice.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.popova.practice.shop.dto.DrinkDto;
import ru.popova.practice.shop.entity.DrinkEntity;
import ru.popova.practice.shop.entity.DrinkOrderEntity;
import ru.popova.practice.shop.repository.DrinkEntityRepository;

import javax.transaction.Transactional;

@Service
public class DrinkService implements AbstractMapper<DrinkEntity, DrinkDto>{
    private DrinkEntityRepository drinkEntityRepository;

    /**
     * получение списка продуктов
     * @param pageable
     * @return список продуетов
     */
    @Transactional
    public Page<DrinkDto> getDrinks(Pageable pageable) {
        return drinkEntityRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    public DrinkDto toDto(DrinkEntity drinkEntity) {
        if (drinkEntity == null) {
            return  null;
        }

        DrinkDto drink = new DrinkDto();
        drink.setName(drinkEntity.getName());
        drink.setPrice(drinkEntity.getPrice());
        drink.setVolume(drinkEntity.getVolume());
        drink.setDescription(drinkEntity.getDescription());
        return drink;
    }

    @Autowired
    public DrinkService(DrinkEntityRepository drinkEntityRepository) {
        this.drinkEntityRepository = drinkEntityRepository;
    }
}
