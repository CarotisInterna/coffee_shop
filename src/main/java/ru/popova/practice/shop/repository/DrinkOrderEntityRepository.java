package ru.popova.practice.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.popova.practice.shop.entity.DrinkOrderEntity;

public interface DrinkOrderEntityRepository extends JpaRepository<DrinkOrderEntity, Integer> {
}
