package ru.popova.practice.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.popova.practice.shop.entity.OrderEntity;
import ru.popova.practice.shop.entity.code.OrderStatusCode;

import java.util.Optional;

public interface OrderEntityRepository extends JpaRepository<OrderEntity, Integer> {
    Optional<OrderEntity> findOneByAppUserIdAndOrderStatusCode(Integer userId, OrderStatusCode code);
}
