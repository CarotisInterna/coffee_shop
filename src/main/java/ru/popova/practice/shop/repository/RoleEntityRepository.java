package ru.popova.practice.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.popova.practice.shop.entity.RoleEntity;
import ru.popova.practice.shop.entity.id.RoleId;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, RoleId> {

}
