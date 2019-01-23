package ru.popova.practice.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.popova.practice.shop.entity.AppUserEntity;

public interface AppUserEntityRepository extends JpaRepository<AppUserEntity, Integer> {
    AppUserEntity findByUsername(String name);
}
