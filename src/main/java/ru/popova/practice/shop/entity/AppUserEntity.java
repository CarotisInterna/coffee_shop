package ru.popova.practice.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "app_user")
@AllArgsConstructor
@NoArgsConstructor
public class AppUserEntity extends AbstractCoffeeShopEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_user_id_seq")
    private Integer id;

    private String username;
    private String fullName;
    private String password;
    private String phoneNumber;
    private String address;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private RoleEntity role;

    @OneToMany(mappedBy = "appUser", fetch = FetchType.LAZY)
    private List<OrderEntity> orders;
}
