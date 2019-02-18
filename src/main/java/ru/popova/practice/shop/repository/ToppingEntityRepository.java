package ru.popova.practice.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.popova.practice.shop.entity.ToppingEntity;

public interface ToppingEntityRepository extends JpaRepository<ToppingEntity, Integer> {
    ToppingEntity findToppingEntityByName(String name);
}
