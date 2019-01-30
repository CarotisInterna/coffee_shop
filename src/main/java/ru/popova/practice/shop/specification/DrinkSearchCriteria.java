package ru.popova.practice.shop.specification;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * класс параметров для поиска напитков
 */
@Builder
@Getter
public class DrinkSearchCriteria {
    /**
     * название напитка
     */
    private String name;
    /**
     * нижняя граница цены
     */
    private BigDecimal lowerPrice;
    /**
     * верхняя граница цены
     */
    private BigDecimal upperPrice;
    /**
     * нижняя граница объема
     */
    private Integer lowerVolume;
    /**
     * верхняя граница объема
     */
    private Integer upperVolume;
    /**
     * идентификатор категории
     */
    private Integer categoryId;
}
