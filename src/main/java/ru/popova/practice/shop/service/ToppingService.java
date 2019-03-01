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
import ru.popova.practice.shop.exception.NotFoundException;
import ru.popova.practice.shop.exception.ValidationException;
import ru.popova.practice.shop.mapper.PageMapper;
import ru.popova.practice.shop.mapper.ToppingMapper;
import ru.popova.practice.shop.repository.ToppingEntityRepository;

import java.util.Optional;

import static ru.popova.practice.shop.util.MessageConstants.TOPPING_NOT_FOUND;
import static ru.popova.practice.shop.util.MessageConstants.TOPPING_UNIQUE;

@Service
@RequiredArgsConstructor
public class ToppingService {

    private final ToppingMapper toppingMapper;
    private final PageMapper pageMapper;
    private final ToppingEntityRepository toppingEntityRepository;
    private final MessageSourceDecorator messageSourceDecorator;

    /**
     * Сохранение топпинга
     *
     * @param toppingDto дто топпинга
     * @return дто сохраненного топпинга
     */
    @Transactional
    public ToppingDto saveTopping(ToppingDto toppingDto) {
        ToppingEntity toppingEntity = toppingMapper.toEntity(toppingDto);
        ToppingEntity saved = toppingEntityRepository.save(toppingEntity);
        return toppingMapper.toDto(saved);
    }

    /**
     * Редактирование топпинга
     *
     * @param toppingDto дто топпинга
     * @param id         идентификатор топпинга
     * @return дто сохраненного топпинга
     */
    @Transactional
    public ToppingDto editTopping(ToppingDto toppingDto, Integer id) {

        Optional<ToppingDto> saved = getToppingById(id);

        if (!saved.isPresent()) {
            throw new NotFoundException(messageSourceDecorator.getMessage(TOPPING_NOT_FOUND));
        }

        ToppingEntity toppingEntity = toppingMapper.toEntity(toppingDto);
        toppingEntity.setId(id);
        ToppingEntity edited = toppingEntityRepository.save(toppingEntity);
        return toppingMapper.toDto(edited);

    }

    /**
     * Валидация топпинга
     *
     * @param toppingDto дто топпинга
     */
    @Transactional(readOnly = true)
    public ListErrorDto validateTopping(ToppingDto toppingDto) {
        ListErrorDto listErrorDto = new ListErrorDto();

        if (getToppingByName(toppingDto.getName()) != null) {
            listErrorDto.addErrorDto("name", messageSourceDecorator.getMessage(TOPPING_UNIQUE));
        }

        return listErrorDto;
    }

    /**
     * удаление топпинга по id
     *
     * @param id идентификатор топпинга
     */
    @Transactional
    public void deleteTopping(Integer id) {
        toppingEntityRepository.findById(id).ifPresent(toppingEntityRepository::delete);
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

    /**
     * Получение топпинга по наименованию
     *
     * @param name наименование топпинга
     * @return топпинг
     */
    @Transactional(readOnly = true)
    public ToppingDto getToppingByName(String name) {
        return toppingMapper.toDto(toppingEntityRepository.findToppingEntityByName(name));
    }

}
