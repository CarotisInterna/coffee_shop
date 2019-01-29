package ru.popova.practice.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageDto<T> {
    /**
     * контент страницы
     */
    private List<T> content;
    /**
     * всего страниц
     */
    private Integer totalPages;
    /**
     * всего элементов
     */
    private Long totalElements;
    /**
     * номер текущей страницы
     */
    private Integer number;
    /**
     * количество элементов на странице
     */
    private Integer numberOfElements;
    /**
     * максимальное колисетво элементов на странице
     */
    private Integer size;
}
