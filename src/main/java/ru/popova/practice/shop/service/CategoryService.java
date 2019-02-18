package ru.popova.practice.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.popova.practice.shop.config.messages.MessageSourceDecorator;
import ru.popova.practice.shop.dto.CategoryDto;
import ru.popova.practice.shop.dto.ListErrorDto;
import ru.popova.practice.shop.dto.PageDto;
import ru.popova.practice.shop.entity.CategoryEntity;
import ru.popova.practice.shop.exception.ValidationException;
import ru.popova.practice.shop.mapper.CategoryMapper;
import ru.popova.practice.shop.mapper.PageMapper;
import ru.popova.practice.shop.repository.CategoryEntityRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final PageMapper pageMapper;
    private final CategoryEntityRepository categoryEntityRepository;
    private final MessageSourceDecorator messageSourceDecorator;

    /**
     * получение категорий
     *
     * @param pageable
     * @return список категорий
     */
    @Transactional
    public PageDto<CategoryDto> getCategories(Pageable pageable) {
        Page<CategoryDto> categories = categoryEntityRepository.findAll(pageable)
                .map(categoryMapper::toDto);
        return pageMapper.toDto(categories);
    }


    /**
     * Поиск категории по id
     *
     * @param id идентификатор напитка
     * @return дто категории
     */
    @Transactional
    public Optional<CategoryDto> getCategoryById(Integer id) {
        return categoryEntityRepository.findById(id)
                .map(categoryMapper::toDto);
    }

    /**
     * Поиск ктегории по названию
     *
     * @param name название
     * @return дто категории
     */
    @Transactional
    public CategoryDto getCategoryByName(String name) {
        return categoryMapper.toDto(categoryEntityRepository.findCategoryEntityByName(name));
    }

    /**
     * Сохранение категории
     *
     * @param categoryDto   дто категории
     * @param bindingResult
     * @return дто сохраненной категории
     */
    @Transactional
    public CategoryDto saveCategory(CategoryDto categoryDto, BindingResult bindingResult) {

        ListErrorDto listErrorDto = new ListErrorDto();

        if (getCategoryByName(categoryDto.getName()) != null) {
            listErrorDto.addErrorDto("name", messageSourceDecorator.getMessage("CategoryUnique.message"));
        }

        if (bindingResult.hasErrors() || !listErrorDto.getErrorDtos().isEmpty()) {
            throw new ValidationException(bindingResult, listErrorDto);
        }

        CategoryEntity categoryEntity = categoryMapper.toEntity(categoryDto);
        CategoryEntity saved = categoryEntityRepository.save(categoryEntity);
        return categoryMapper.toDto(saved);
    }

}
