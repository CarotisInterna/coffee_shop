package ru.popova.practice.shop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.popova.practice.shop.config.messages.MessageSourceDecorator;
import ru.popova.practice.shop.dto.*;
import ru.popova.practice.shop.entity.*;
import ru.popova.practice.shop.exception.NotFoundException;
import ru.popova.practice.shop.mapper.DrinkMapper;
import ru.popova.practice.shop.mapper.NewDrinkMapper;
import ru.popova.practice.shop.mapper.PageMapper;
import ru.popova.practice.shop.repository.CategoryEntityRepository;
import ru.popova.practice.shop.repository.DrinkEntityRepository;
import ru.popova.practice.shop.specification.DrinkSearchCriteria;
import ru.popova.practice.shop.specification.SpecificationBuilder;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.popova.practice.shop.util.constants.MessageConstants.CATEGORY_NOT_FOUND;
import static ru.popova.practice.shop.util.constants.MessageConstants.DRINK_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class DrinkService {

    private final DrinkMapper drinkMapper;
    private final PageMapper pageMapper;
    private final NewDrinkMapper newDrinkMapper;
    private final DrinkEntityRepository drinkEntityRepository;
    private final CategoryEntityRepository categoryEntityRepository;
    private final MessageSourceDecorator messageSourceDecorator;
    private final ImageService imageService;

    /**
     * получение списка напитков
     *
     * @param pageable
     * @return список напитков
     */
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public List<DrinkDto> getDrinksByCategory(Integer id) {
        Optional<CategoryEntity> category = getCategoryEntity(id);
        if (!category.isPresent()) {
            return Collections.emptyList();
        }
        return drinkEntityRepository.findAllByCategoriesContains(category.get())
                .stream()
                .map(drinkMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * сохранение напитка
     *
     * @param newDrinkDto новый напиток
     * @return напиток
     */
    @Transactional
    public DrinkDto saveDrink(NewDrinkDto newDrinkDto) {

        ListErrorDto listErrorDto = new ListErrorDto();

        checkIfCategoriesPresent(newDrinkDto, listErrorDto);

        DrinkEntity drinkEntity = newDrinkMapper.toEntity(newDrinkDto);
        DrinkEntity saved = drinkEntityRepository.save(drinkEntity);
        List<DrinkImageEntity> imageEntities = imageService.saveImages(newDrinkDto.getImages(), saved);

        saved.setImages(imageEntities);

        return drinkMapper.toDto(saved);
    }

    /**
     * редактирование напитка
     *
     * @param newDrinkDto дто напитка
     * @param id          идентификатор напитка
     * @return напиток
     */

    @Transactional
    public DrinkDto editDrink(NewDrinkDto newDrinkDto, Integer id) {
        Optional<DrinkDto> found = getDrinkById(id);

        if (!found.isPresent()) {
            throw new NotFoundException(messageSourceDecorator.getMessage(DRINK_NOT_FOUND));
        }

        ListErrorDto listErrorDto = new ListErrorDto();

        checkIfCategoriesPresent(newDrinkDto, listErrorDto);

        DrinkEntity drinkEntity = newDrinkMapper.toEntity(newDrinkDto);
        drinkEntity.setId(id);
        drinkEntity.setImages(imageService.getImagesByDrinkId(id)); //dirty hack

        DrinkEntity edited = drinkEntityRepository.save(drinkEntity);

        return drinkMapper.toDto(edited);
    }

    /**
     * удаление напитка
     *
     * @param id идентификатор напитка
     */
    @Transactional
    public void deleteDrink(Integer id) {
        drinkEntityRepository.findById(id).ifPresent(drinkEntityRepository::delete);
    }

    /**
     * поиск напитков по заданным параметрам
     *
     * @param drinkSearchCriteria параметры поиска
     * @param pageable
     * @return список напитков
     */
    @Transactional(readOnly = true)
    public PageDto<DrinkDto> search(DrinkSearchCriteria drinkSearchCriteria, Pageable pageable) {
        SpecificationBuilder<DrinkEntity> specificationBuilder = new SpecificationBuilder<>();

        Optional.ofNullable(drinkSearchCriteria.getName())
                .filter(name -> !name.isEmpty())
                .ifPresent(name ->
                        specificationBuilder.with(((root, query, builder) ->
                                builder.like(
                                        builder.upper(root.get(DrinkEntity_.name)),
                                        "%" + name.toUpperCase() + "%"
                                )
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
     * проверка наличия указанных категорий
     *
     * @param newDrinkDto  дто напитка
     * @param listErrorDto список полей и ошибок, связанных с этим полем
     */
    @Transactional(readOnly = true)
    public void checkIfCategoriesPresent(NewDrinkDto newDrinkDto, ListErrorDto listErrorDto) {
        for (Integer categoryId : newDrinkDto.getCategories()) {
            if (!getCategoryEntity(categoryId).isPresent()) {
                listErrorDto.addErrorDto("categories", categoryId + " " + messageSourceDecorator.getMessage(CATEGORY_NOT_FOUND));
            }
        }

        if (!listErrorDto.getErrorDtos().isEmpty()) {
            throw new NotFoundException(listErrorDto);
        }
    }

    /**
     * получение напитка по объему и наименованию
     *
     * @param name   н6аименование напитка
     * @param volume объем напитка
     * @return дто напитка
     */

    @Transactional(readOnly = true)
    public DrinkDto getDrinkByNameAndVolume(String name, Integer volume) {
        return drinkMapper.toDto(drinkEntityRepository.findOneByNameAndVolume(name, volume));
    }

    /**
     * получение категории напитка по идентификатору
     *
     * @param id идентификатор
     * @return сущность категории
     */
    private Optional<CategoryEntity> getCategoryEntity(Integer id) {
        return categoryEntityRepository.findById(id);
    }

}
