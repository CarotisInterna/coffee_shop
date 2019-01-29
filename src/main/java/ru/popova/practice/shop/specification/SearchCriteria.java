package ru.popova.practice.shop.specification;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public class SearchCriteria {

    private String name;
    private BigDecimal lowerPrice;
    private BigDecimal upperPrice;
    private Integer lowerVolume;
    private Integer upperVolume;
    private Integer categoryId;
}
