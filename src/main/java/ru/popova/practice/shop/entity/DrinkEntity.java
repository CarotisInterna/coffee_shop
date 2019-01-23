package ru.popova.practice.shop.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "drink")
public class DrinkEntity extends AbstractCoffeeShopEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String name;
    private BigDecimal price;
    private Integer volume;
    private String description;

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

    @OneToMany(mappedBy = "drink")
    private List<DrinkImageEntity> images;

}
