package ru.popova.practice.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.popova.practice.shop.entity.OrderEntity;

public interface OrderEntityRepository extends JpaRepository<OrderEntity, Integer> {
}
