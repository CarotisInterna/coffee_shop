package ru.popova.practice.shop.entity;

import lombok.Getter;
import lombok.Setter;
import ru.popova.practice.shop.entity.entity_enum.CategoryId;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "category")
public class CategoryEntity {
    @Id
    @Enumerated(EnumType.ORDINAL)
    private CategoryId id;
    private String name;

    @OneToMany(mappedBy = "category")
    private List<DrinkCategoryEntity> drinkCategory;
}
