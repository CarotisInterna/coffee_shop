package ru.popova.practice.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.popova.practice.shop.entity.code.RoleCode;

import javax.persistence.*;

/**
 * класс сущности роли пользователя
 */
@Getter
@Setter
@Entity
@Table(name = "role")
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity extends AbstractCoffeeShopEntity<RoleCode> {
    @Id
    private Integer id;

    /**
     * код роли пользователя
     */
    @Enumerated(EnumType.ORDINAL)
    private RoleCode code;
    /**
     * наименование роли пользователя
     */
    private String name;
}
