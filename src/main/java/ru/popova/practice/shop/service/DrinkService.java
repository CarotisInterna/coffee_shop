package ru.popova.practice.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.popova.practice.shop.dto.DrinkDto;
import ru.popova.practice.shop.dto.NewDrinkDto;
import ru.popova.practice.shop.dto.PageDto;
import ru.popova.practice.shop.entity.CategoryEntity;
import ru.popova.practice.shop.entity.CategoryEntity_;
import ru.popova.practice.shop.entity.DrinkEntity;
import ru.popova.practice.shop.entity.DrinkEntity_;
import ru.popova.practice.shop.mapper.DrinkMapper;
import ru.popova.practice.shop.mapper.NewDrinkMapper;
import ru.popova.practice.shop.mapper.PageMapper;
import ru.popova.practice.shop.repository.CategoryEntityRepository;
import ru.popova.practice.shop.repository.DrinkEntityRepository;
import ru.popova.practice.shop.specification.DrinkSearchCriteria;
import ru.popova.practice.shop.specification.SpecificationBuilder;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DrinkService {

    private final DrinkMapper drinkMapper;
    private final PageMapper pageMapper;
    private final NewDrinkMapper newDrinkMapper;
    private final DrinkEntityRepository drinkEntityRepository;
    private final CategoryEntityRepository categoryEntityRepository;

    /**
     * получение списка напитков
     *
     * @param pageable
     * @return список напитков
     */
    @Transactional
    public Page<DrinkDto> getDrinks(Pageable pageable) {
        return drinkEntityRepository.findAll(pageable)
                .map(drinkMapper::toDto);
    }

    /**
     * получение напитка по id
     *
     * @param id идентификатор напитка
     * @return дто напитка
     */
    @Transactional
    public Optional<DrinkDto> getDrinkById(Integer id) {
        return drinkEntityRepository.findById(id)
                .map(drinkMapper::toDto);
    }

    /**
     * получение списка напитков конкретной категории
     *
     * @param id идентификатор напитка
     * @return список напитков
     */
    @Transactional
    public List<DrinkDto> getDrinksByCategory(Integer id) {
        Optional<CategoryEntity> category = categoryEntityRepository.findById(id);
        if (!category.isPresent()) {
            return Collections.emptyList();
        }
        return drinkEntityRepository.findAllByCategoriesContains(category.get())
                .stream()
                .map(drinkMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * поиск напитков по заданным параметрам
     *
     * @param drinkSearchCriteria параметры поиска
     * @param pageable
     * @return список напитков
     */
    @Transactional
    public PageDto<DrinkDto> search(DrinkSearchCriteria drinkSearchCriteria, Pageable pageable) {
        SpecificationBuilder<DrinkEntity> specificationBuilder = new SpecificationBuilder<>();

        Optional.ofNullable(drinkSearchCriteria.getName())
                .filter(name -> !name.isEmpty())
                .ifPresent(name ->
                        specificationBuilder.with(((root, query, builder) ->
                                builder.like(root.get(DrinkEntity_.name), name)
                        )));

        BigDecimal lowerPrice = drinkSearchCriteria.getLowerPrice();
        BigDecimal upperPrice = drinkSearchCriteria.getUpperPrice();

        specificationBuilder.between(DrinkEntity_.price, lowerPrice, upperPrice);

        Integer lowerVolume = drinkSearchCriteria.getLowerVolume();
        Integer upperVolume = drinkSearchCriteria.getUpperVolume();

        specificationBuilder.between(DrinkEntity_.volume, lowerVolume, upperVolume);

        Optional.ofNullable(drinkSearchCriteria.getCategoryId())
                .ifPresent(categoryId ->
                        specificationBuilder.with(((root, query, builder) -> {
                            Join<DrinkEntity, CategoryEntity> join = root.join(DrinkEntity_.categories, JoinType.LEFT);
                            return builder.equal(join.get(CategoryEntity_.id), categoryId);
                        })));

        Page<DrinkDto> drinks = drinkEntityRepository.findAll(Specification.where(specificationBuilder.build()), pageable).map(drinkMapper::toDto);
        return pageMapper.toDto(drinks);
    }

    /**
     * сохранение напитка
     *
     * @param newDrinkDto новый напиток
     * @return напиток
     */
    @Transactional
    public DrinkDto saveDrink(NewDrinkDto newDrinkDto) {
        DrinkEntity drinkEntity = newDrinkMapper.toEntity(newDrinkDto);
        DrinkEntity saved = drinkEntityRepository.save(drinkEntity);
        log.info("{}", saved.getId());
        return drinkMapper.toDto(saved);
    }

}
