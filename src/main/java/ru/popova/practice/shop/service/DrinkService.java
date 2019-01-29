package ru.popova.practice.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.popova.practice.shop.dto.DrinkDto;
import ru.popova.practice.shop.entity.CategoryEntity;
import ru.popova.practice.shop.entity.CategoryEntity_;
import ru.popova.practice.shop.entity.DrinkEntity;
import ru.popova.practice.shop.entity.DrinkEntity_;
import ru.popova.practice.shop.mapper.DrinkMapper;
import ru.popova.practice.shop.repository.CategoryEntityRepository;
import ru.popova.practice.shop.repository.DrinkEntityRepository;
import ru.popova.practice.shop.specification.SpecificationBuilder;
import ru.popova.practice.shop.specification.SearchCriteria;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DrinkService {

    private DrinkMapper drinkMapper;
    private DrinkEntityRepository drinkEntityRepository;
    private CategoryEntityRepository categoryEntityRepository;

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

    @Transactional
    public Page<DrinkDto> search(SearchCriteria searchCriteria, Pageable pageable) {
        SpecificationBuilder<DrinkEntity> specificationBuilder = new SpecificationBuilder<>();

        Optional.ofNullable(searchCriteria.getName())
                .ifPresent(name ->
                        specificationBuilder.with(((root, query, builder) ->
                                builder.like(root.get(DrinkEntity_.name), name)
                        )));

        BigDecimal lowerPrice = searchCriteria.getLowerPrice();
        BigDecimal upperPrice = searchCriteria.getUpperPrice();

        specificationBuilder.between(DrinkEntity_.price, lowerPrice, upperPrice);

        Integer lowerVolume = searchCriteria.getLowerVolume();
        Integer upperVolume = searchCriteria.getUpperVolume();

        specificationBuilder.between(DrinkEntity_.volume, lowerVolume, upperVolume);

        Optional.ofNullable(searchCriteria.getCategoryId())
                .ifPresent(categoryId ->
                        specificationBuilder.with(((root, query, builder) -> {
                            Join<DrinkEntity, CategoryEntity> join = root.join(DrinkEntity_.categories, JoinType.LEFT);
                            return builder.equal(join.get(CategoryEntity_.id), categoryId);
                        })));


        return drinkEntityRepository.findAll(Specification.where(specificationBuilder.build()), pageable).map(drinkMapper::toDto);
    }

    @Autowired
    public DrinkService(DrinkEntityRepository drinkEntityRepository, DrinkMapper drinkMapper, CategoryEntityRepository categoryEntityRepository) {
        this.drinkEntityRepository = drinkEntityRepository;
        this.drinkMapper = drinkMapper;
        this.categoryEntityRepository = categoryEntityRepository;
    }
}
