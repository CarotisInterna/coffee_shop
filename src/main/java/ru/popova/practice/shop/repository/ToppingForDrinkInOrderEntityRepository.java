package ru.popova.practice.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.popova.practice.shop.entity.ToppingForDrinkInOrderEntity;
import ru.popova.practice.shop.entity.ToppingForDrinkInOrderId;

public interface ToppingForDrinkInOrderEntityRepository extends JpaRepository<ToppingForDrinkInOrderEntity, ToppingForDrinkInOrderId> {
}
