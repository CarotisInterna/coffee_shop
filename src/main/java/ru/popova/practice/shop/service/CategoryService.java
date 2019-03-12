package ru.popova.practice.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.popova.practice.shop.config.messages.MessageSourceDecorator;
import ru.popova.practice.shop.dto.CategoryDto;
import ru.popova.practice.shop.dto.ListErrorDto;
import ru.popova.practice.shop.entity.CategoryEntity;
import ru.popova.practice.shop.exception.NotAllowedException;
import ru.popova.practice.shop.exception.NotFoundException;
import ru.popova.practice.shop.mapper.CategoryMapper;
import ru.popova.practice.shop.mapper.PageMapper;
import ru.popova.practice.shop.repository.CategoryEntityRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.popova.practice.shop.util.constants.MessageConstants.*;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final PageMapper pageMapper;
    private final CategoryEntityRepository categoryEntityRepository;
    private final MessageSourceDecorator messageSourceDecorator;
    private final DrinkService drinkService;

    /**
     * Сохранение категории
     *
     * @param categoryDto дто категории
     * @return дто сохраненной категории
     */
    @Transactional
    public CategoryDto saveCategory(CategoryDto categoryDto) {

        CategoryEntity categoryEntity = categoryMapper.toEntity(categoryDto);
        CategoryEntity saved = categoryEntityRepository.save(categoryEntity);
        return categoryMapper.toDto(saved);
    }

    /**
     * Редактирование категории
     *
     * @param categoryDto категория
     * @return измененная категория
     */
    @Transactional
    public CategoryDto editCategory(CategoryDto categoryDto, Integer id) {

        Optional<CategoryDto> saved = getCategoryById(id);

        if (!saved.isPresent()) {
            throw new NotFoundException(messageSourceDecorator.getMessage(CATEGORY_NOT_FOUND));
        }

        CategoryEntity categoryEntity = categoryMapper.toEntity(categoryDto);
        categoryEntity.setId(id);
        CategoryEntity edited = categoryEntityRepository.save(categoryEntity);
        return categoryMapper.toDto(edited);
    }

    /**
     * Валидация категории, которой не было через аннотации
     *
     * @param categoryDto дто категории
     * @return список ошибок
     */
    @Transactional(readOnly = true)
    public ListErrorDto validateCategory(CategoryDto categoryDto) {
        ListErrorDto listErrorDto = new ListErrorDto();

        if (getCategoryByName(categoryDto.getName()) != null) {
            listErrorDto.addErrorDto("name", messageSourceDecorator.getMessage(CATEGORY_UNIQUE));
        }

        return listErrorDto;
    }

    /**
     * Удаление категории по id
     *
     * @param id идентификать категории
     */
    @Transactional
    public void deleteCategory(Integer id) {
        if (!drinkService.getDrinksByCategory(id).isEmpty()) {
            throw new NotAllowedException("category", messageSourceDecorator.getMessage(DRINK_WITH_THIS_CATEGORY_EXISTS));
        }
        categoryEntityRepository.findById(id).ifPresent(categoryEntityRepository::delete);
    }

    /**
     * Получение категорий
     *
     * @return список категорий
     */
    @Transactional(readOnly = true)
    public List<CategoryDto> getCategories() {
        return categoryEntityRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }


    /**
     * Поиск категории по id
     *
     * @param id идентификатор напитка
     * @return дто категории
     */
    @Transactional(readOnly = true)
    public Optional<CategoryDto> getCategoryById(Integer id) {
        return categoryEntityRepository.findById(id)
                .map(categoryMapper::toDto);
    }

    /**
     * Поиск категории по названию
     *
     * @param name название
     * @return дто категории
     */
    @Transactional(readOnly = true)
    public CategoryDto getCategoryByName(String name) {
        return categoryMapper.toDto(categoryEntityRepository.findCategoryEntityByName(name));
    }
}
