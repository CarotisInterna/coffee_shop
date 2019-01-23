package ru.popova.practice.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.popova.practice.shop.entity.id.RoleId;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "role")
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity extends AbstractCoffeeShopEntity<RoleId> {
    @Id
    @Enumerated(EnumType.ORDINAL)
    private RoleId id;
    private String name;
}
