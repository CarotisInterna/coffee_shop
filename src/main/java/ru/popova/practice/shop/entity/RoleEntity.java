package ru.popova.practice.shop.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import ru.popova.practice.shop.domain.RoleId;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DynamicUpdate
@Table(name = "role")
public class RoleEntity  {
    @Id
    @Enumerated(EnumType.ORDINAL)
    private RoleId id;
    private String name;
}
