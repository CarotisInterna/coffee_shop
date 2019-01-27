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
import java.util.Optional;

@Service
public class DrinkService implements AbstractMapper<DrinkEntity, DrinkDto>{
    private DrinkEntityRepository drinkEntityRepository;

    /**
     * получение списка продуктов
     * @param pageable параметры запроса
     * @return список продуктов
     */
    @Transactional
    public Page<DrinkDto> getDrinks(Pageable pageable) {
        return drinkEntityRepository.findAll(pageable).map(this::toDto);
    }

    /**
     * получение напитка по id
     * @param id идентификатор напитка
     * @return напиток
     */
    @Transactional
    public Optional<DrinkDto> getDrinkById(Integer id) {
        Optional<DrinkEntity> drinkEntity = drinkEntityRepository.findById(id);
        return drinkEntity.map(this::toDto);
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
