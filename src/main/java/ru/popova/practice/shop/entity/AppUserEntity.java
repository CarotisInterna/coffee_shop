package ru.popova.practice.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * класс сущности пользователя приложения
 */
@Getter
@Setter
@Entity
@Table(name = "app_user")
@AllArgsConstructor
@NoArgsConstructor
public class AppUserEntity extends AbstractCoffeeShopEntity<Integer> {

    /**
     * уникальный идентификатор
     */
    @Id
//    @SequenceGenerator(name = "app_user_id_seq", sequenceName = "app_user_id_seq")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_user_id_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * логин пользователя (уникальный)
     */
    private String username;
    /**
     * полное имя пользователя
     */
    private String fullName;
    /**
     * пароль пользователя
     */
    private String password;
    /**
     * номер телефона пользователя
     */
    private String phoneNumber;
    /**
     * адрес пользователя
     */
    private String address;

    /**
     * роль пользователя
     */
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private RoleEntity role;

    /**
     * список заказов пользователя
     */
    @OneToMany(mappedBy = "appUser", fetch = FetchType.LAZY)
    private List<OrderEntity> orders;
}
