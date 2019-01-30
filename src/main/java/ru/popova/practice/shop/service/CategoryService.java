package ru.popova.practice.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
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
public class CategoryService {

    private CategoryMapper categoryMapper;
    private PageMapper pageMapper;
    private CategoryEntityRepository categoryEntityRepository;

    @Transactional
    public PageDto<CategoryDto> getCategories(Pageable pageable){
        Page<CategoryDto> categories = categoryEntityRepository.findAll(pageable)
                .map(categoryMapper::toDto);
        return pageMapper.toDto(categories);
    }

    public CategoryService(CategoryMapper categoryMapper, PageMapper pageMapper, CategoryEntityRepository categoryEntityRepository) {
        this.categoryMapper = categoryMapper;
        this.pageMapper = pageMapper;
        this.categoryEntityRepository = categoryEntityRepository;
    }
}
