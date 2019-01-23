package ru.popova.practice.shop.entity;

import lombok.Getter;
import lombok.Setter;
import ru.popova.practice.shop.entity.id.CategoryId;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "category")
public class CategoryEntity extends AbstractCoffeeShopEntity<CategoryId>{
    @Id
    @Enumerated(EnumType.ORDINAL)
    private CategoryId id;
    private String name;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private List<DrinkEntity> drinks;
}
