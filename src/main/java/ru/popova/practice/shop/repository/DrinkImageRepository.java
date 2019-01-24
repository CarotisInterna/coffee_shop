package ru.popova.practice.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.popova.practice.shop.entity.DrinkImageEntity;

public interface DrinkImageRepository extends JpaRepository<DrinkImageEntity, Integer> {
}
