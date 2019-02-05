package ru.popova.practice.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * класс сущности категории напитков
 */
@Getter
@Setter
@Entity
@Table(name = "category")
public class CategoryEntity extends AbstractCoffeeShopEntity<Integer>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     *наименования категории напитков
     */
    @Column(name = "category_name")
    private String name;

    /**
     *список напитков для категории
     */
    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private List<DrinkEntity> drinks;
}
