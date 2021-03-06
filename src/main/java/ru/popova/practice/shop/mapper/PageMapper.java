package ru.popova.practice.shop.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.popova.practice.shop.dto.PageDto;

import static ru.popova.practice.shop.util.constants.NumConstants.NUMBER_OF_ELEMENTS_ON_PAGE;

@Component
public class PageMapper implements AbstractMapper<Page, PageDto> {

    @Override
    public PageDto toDto(Page entity) {
        if (entity == null) {
            return null;
        }

        PageDto pageDto = new PageDto();
        pageDto.setContent(entity.getContent());
        pageDto.setNumber(entity.getNumber());
        pageDto.setNumberOfElements(entity.getNumberOfElements());
        pageDto.setTotalElements(entity.getTotalElements());
        pageDto.setTotalPages(entity.getTotalPages());
        pageDto.setSize(NUMBER_OF_ELEMENTS_ON_PAGE);
        return pageDto;
    }

    @Override
    public Page toEntity(PageDto dto) {
        return null;
    }
}
