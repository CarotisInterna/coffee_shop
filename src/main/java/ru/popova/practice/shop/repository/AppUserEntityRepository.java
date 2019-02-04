package ru.popova.practice.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.popova.practice.shop.entity.AppUserEntity;

public interface AppUserEntityRepository extends CrudRepository<AppUserEntity, Integer> {
    AppUserEntity findAppUserEntityByUsername(String username);
}
