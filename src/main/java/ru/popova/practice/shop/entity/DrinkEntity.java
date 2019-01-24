package ru.popova.practice.shop.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * класс сущности напитка
 */
@Getter
@Setter
@Entity
@Table(name = "drink")
public class DrinkEntity extends AbstractCoffeeShopEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    /**
     *наименование напитка
     */
    private String name;
    /**
     *цена напитка
     */
    private BigDecimal price;
    /**
     *объем напитка
     */
    private Integer volume;
    /**
     *описание напитка
     */
    private String description;

    /**
     *список категорий напитка
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "drink_category",
            joinColumns = {
                    @JoinColumn(name = "drink_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "category_id")
            }
    )
    private List<CategoryEntity> categories;

    /**
     *список изображений
     */
    @OneToMany(mappedBy = "drink")
    private List<DrinkImageEntity> images;

}
