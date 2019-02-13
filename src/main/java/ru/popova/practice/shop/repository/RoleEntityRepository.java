package ru.popova.practice.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.popova.practice.shop.entity.RoleEntity;
import ru.popova.practice.shop.entity.code.RoleCode;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, RoleCode> {
    RoleEntity findRoleEntityByCode(RoleCode code);
}
