package ru.popova.practice.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.popova.practice.shop.dto.PageDto;
import ru.popova.practice.shop.dto.ToppingDto;
import ru.popova.practice.shop.mapper.PageMapper;
import ru.popova.practice.shop.mapper.ToppingMapper;
import ru.popova.practice.shop.repository.ToppingEntityRepository;

import javax.transaction.Transactional;

@Service
public class ToppingService {

    private ToppingMapper toppingMapper;
    private PageMapper pageMapper;
    private ToppingEntityRepository toppingEntityRepository;

    @Transactional
    public PageDto<ToppingDto> getToppings(Pageable pageable){
        Page<ToppingDto> toppings = toppingEntityRepository.findAll(pageable)
                .map(toppingMapper::toDto);
        return pageMapper.toDto(toppings);
    }

    @Autowired
    public ToppingService(ToppingMapper toppingMapper, ToppingEntityRepository toppingEntityRepository, PageMapper pageMapper) {
        this.toppingMapper = toppingMapper;
        this.pageMapper = pageMapper;
        this.toppingEntityRepository = toppingEntityRepository;
    }
}
