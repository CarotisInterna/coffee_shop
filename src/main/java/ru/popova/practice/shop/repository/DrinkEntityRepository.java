package ru.popova.practice.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.popova.practice.shop.entity.DrinkEntity;

public interface DrinkEntityRepository extends JpaRepository<DrinkEntity, Integer> {
}
