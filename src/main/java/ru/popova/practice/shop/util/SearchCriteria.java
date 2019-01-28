package ru.popova.practice.shop.util;

import lombok.Builder;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;

@Builder
public class SearchCriteria {

   private String name;
   private BigDecimal price;
   private Integer volume;
   private Integer categoryId;
}
