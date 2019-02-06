package ru.popova.practice.shop.service;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ToppingService {

    private final ToppingMapper toppingMapper;
    private final PageMapper pageMapper;
    private final ToppingEntityRepository toppingEntityRepository;

    /**
     * получение топпингов
     *
     * @param pageable
     * @return список топпингов
     */
    @Transactional
    public PageDto<ToppingDto> getToppings(Pageable pageable) {
        Page<ToppingDto> toppings = toppingEntityRepository.findAll(pageable)
                .map(toppingMapper::toDto);
        return pageMapper.toDto(toppings);
    }

}
