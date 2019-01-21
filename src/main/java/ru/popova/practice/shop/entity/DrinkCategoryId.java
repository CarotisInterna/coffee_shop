package ru.popova.practice.shop.entity;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"drinkId", "personId"})
public class DrinkCategoryId implements Serializable {
    private Integer drinkId;
    private Integer categoryId;
}
