package ru.popova.practice.shop.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DynamicUpdate
@Table(name = "app_user")
public class AppUserEntity {
    @Id
    private Integer id;
    private String username;
    private String fullName;
    private String phoneNumber;
    private String address;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;
}
