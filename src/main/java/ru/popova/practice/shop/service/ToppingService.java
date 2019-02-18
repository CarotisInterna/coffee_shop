package ru.popova.practice.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ru.popova.practice.shop.config.messages.MessageSourceDecorator;
import ru.popova.practice.shop.dto.ListErrorDto;
import ru.popova.practice.shop.dto.PageDto;
import ru.popova.practice.shop.dto.ToppingDto;
import ru.popova.practice.shop.entity.ToppingEntity;
import ru.popova.practice.shop.exception.ValidationException;
import ru.popova.practice.shop.mapper.PageMapper;
import ru.popova.practice.shop.mapper.ToppingMapper;
import ru.popova.practice.shop.repository.ToppingEntityRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToppingService {

    private final ToppingMapper toppingMapper;
    private final PageMapper pageMapper;
    private final ToppingEntityRepository toppingEntityRepository;
    private final MessageSourceDecorator messageSourceDecorator;

    @Transactional
    public ToppingDto saveTopping(ToppingDto toppingDto, BindingResult bindingResult) {

        ListErrorDto listErrorDto = new ListErrorDto();

        if (getToppingByName(toppingDto.getName()) != null) {
            listErrorDto.addErrorDto("name", messageSourceDecorator.getMessage("ToppingUnique.message"));
        }

        if (bindingResult.hasErrors() || !listErrorDto.getErrorDtos().isEmpty()) {
            throw new ValidationException(bindingResult, listErrorDto);
        }

        ToppingEntity toppingEntity = toppingMapper.toEntity(toppingDto);
        ToppingEntity saved = toppingEntityRepository.save(toppingEntity);
        return toppingMapper.toDto(saved);
    }

    /**
     * Получение топпингов
     *
     * @param pageable
     * @return список топпингов
     */
    @Transactional(readOnly = true)
    public PageDto<ToppingDto> getToppings(Pageable pageable) {
        Page<ToppingDto> toppings = toppingEntityRepository.findAll(pageable)
                .map(toppingMapper::toDto);
        return pageMapper.toDto(toppings);
    }

    /**
     * Поиск топпинга по id
     *
     * @param id идентификатор топпинга
     * @return дто топпинга
     */
    @Transactional(readOnly = true)
    public Optional<ToppingDto> getToppingById(Integer id) {
        return toppingEntityRepository.findById(id)
                .map(toppingMapper::toDto);
    }

    @Transactional(readOnly = true)
    public ToppingDto getToppingByName(String name) {
        return toppingMapper.toDto(toppingEntityRepository.findToppingEntityByName(name));
    }

}
