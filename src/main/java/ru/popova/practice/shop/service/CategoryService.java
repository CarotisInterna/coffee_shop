package ru.popova.practice.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.popova.practice.shop.dto.CategoryDto;
import ru.popova.practice.shop.dto.PageDto;
import ru.popova.practice.shop.mapper.CategoryMapper;
import ru.popova.practice.shop.mapper.PageMapper;
import ru.popova.practice.shop.repository.CategoryEntityRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final PageMapper pageMapper;
    private final CategoryEntityRepository categoryEntityRepository;

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

}
